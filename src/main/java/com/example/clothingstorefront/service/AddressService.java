package com.example.clothingstorefront.service;

import com.example.clothingstorefront.dto.AddressDTO;
import com.example.clothingstorefront.model.Address;
import com.example.clothingstorefront.repository.AddressRepository;
import com.example.clothingstorefront.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles address management through the repository. Exposes only necessary methods to the controller.
 */
@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepo;

    // dependency for validation checking
    @Autowired
    UserRepository userRepo;

    /**
     * Takes in an address DTO and persists it to the database.
     * @param toAdd The relevant DTO to save
     * @return the saved address
     */
    public Address addAddress(AddressDTO toAdd) {
        // use mapper to map all elements of DTO to our converted value
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Address converted = converter.map(toAdd, Address.class);

        // need to make the connection to the user manually
        converted.setCreatedBy(userRepo.getUserByUserId(toAdd.getUserId()));

        // and return what the repository would normally return
        return addressRepo.save(converted);
    }

    /**
     * Takes in a (mostly empty) address DTO and uses the ID entry to delete it from
     * the database.
     * @param toDelete encapsulated information for deletion operation
     */
    public void deleteAddress(AddressDTO toDelete) {
        addressRepo.deleteByIdAndCreatedBy(toDelete.getId(), userRepo.getUserByUserId(toDelete.getUserId()));
    }
}
