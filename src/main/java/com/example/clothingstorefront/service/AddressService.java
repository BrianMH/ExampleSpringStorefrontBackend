package com.example.clothingstorefront.service;

import com.example.clothingstorefront.dto.AddressDTO;
import com.example.clothingstorefront.model.Address;
import com.example.clothingstorefront.repository.AddressRepository;
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

    /**
     * Takes in an address DTO and persists it to the database.
     * @param toAdd The relevant DTO to save
     * @return the saved address
     */
    public Address addAddress(AddressDTO toAdd) {
        // use mapper to map all elements of DTO to our converted value
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Address converted = converter.map(toAdd, Address.class);

        // and return what the repository would normally return
        return addressRepo.save(converted);
    }
}
