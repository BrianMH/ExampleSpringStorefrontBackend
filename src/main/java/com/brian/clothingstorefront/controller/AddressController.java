package com.brian.clothingstorefront.controller;

import com.brian.clothingstorefront.dto.AddressDTO;
import com.brian.clothingstorefront.dto.ResultDTO;
import com.brian.clothingstorefront.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Unlike for other values, there really is no reason for a non-user to ever have access to PII, so the best way to
 * manage addresses is to only expose the direct API to their respective users.
 *
 * An admin would have access to a given address only through an associated invoice instead.
 */
@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    @Autowired
    AddressService addressSvc;

    /**
     * Receives a given addressId and returns the address, or a ResultDTO representing the failed operation, otherwise
     * @param addressId
     * @return
     */
    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable long addressId) {
        AddressDTO toReturn;
        try {
            toReturn = addressSvc.getAddressById(addressId);
            return ResponseEntity.status(HttpStatus.OK).body(toReturn);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find address"));
        }
    }

    /**
     * Patches an address given an input DTO package and returns the ResultDTO corresponding to the operation success.
     * @param toPatch
     * @return
     */
    @PatchMapping("/patch")
    public ResponseEntity<ResultDTO> updateAddressById(@RequestBody AddressDTO toPatch) {
        try {
            addressSvc.applyAddressPatch(toPatch);
            return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, "Patched"));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find address"));
        }
    }

    /**
     * Takes in a userId and returns a list of addresses that are associated with that UUID (which can be empty)
     * @param userId
     * @return
     */
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<AddressDTO>> getUserAddresses(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(addressSvc.getAddressesByUser(userId));
    }

    /**
     * Takes in a DTO representation of an Address and persists it to the database
     * @param toAdd
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<?> addAddressToUser(@RequestBody AddressDTO toAdd) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(addressSvc.addAddress(toAdd));
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find user."));
        }
    }

    /**
     * Takes in an addressId and deletes that entry
     * @param addressId
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteMessageById(@PathVariable("id")long addressId) {
        // with deletion we should make sure the id actually exists beforehand
        if(!addressSvc.addressExists(addressId))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Invalid ID"));

        // otherwise return a typical no content on successful deletion
        addressSvc.deleteAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, null));
    }}
