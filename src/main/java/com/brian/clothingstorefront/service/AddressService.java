package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.repository.AddressRepository;
import com.brian.clothingstorefront.repository.UserRepository;
import com.brian.clothingstorefront.dto.AddressDTO;
import com.brian.clothingstorefront.model.Address;
import com.brian.clothingstorefront.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
     * Takes in a partially complete object (a patch) and applies it to the indicated Address ID
     * @param patch
     * @throws Exception
     */
    public void applyAddressPatch(AddressDTO patch) throws Exception {
        Optional<Address> relAddress = addressRepo.findById(patch.getId());
        if(relAddress.isEmpty())
            throw new Exception("Address does not exist in database. Aborting operation.");
        Address toChange = relAddress.get();

        // otherwise continue patching as normal by checking values in the DTO
        if(!patch.getFAddress().equals(toChange.getFAddress())) {
            toChange.setFAddress(patch.getFAddress());
        } else if(!patch.getSAddress().equals(toChange.getSAddress())) {
            toChange.setSAddress(patch.getSAddress());
        } else if(!patch.getCity().equals(toChange.getCity())) {
            toChange.setCity(patch.getCity());
        } else if(!patch.getState().equals(toChange.getState())) {
            toChange.setState(patch.getState());
        } else if(!patch.getFullName().equals(toChange.getFullName())) {
            toChange.setFullName(patch.getFullName());
        } else if(!patch.getZip().equals(toChange.getZip())) {
            toChange.setZip(patch.getZip());
        } else if(!patch.getExtraInfo().equals(toChange.getExtraInfo())) {
            toChange.setExtraInfo(patch.getExtraInfo());
        }

        // then persist this value to database with the adjustments
        addressRepo.save(toChange);
    }

    /**
     * Takes in an address DTO and persists it to the database.
     * @param toAdd The relevant DTO to save
     * @return the saved address
     */
    public AddressDTO addAddress(AddressDTO toAdd) throws Exception {
        // use mapper to map all elements of DTO to our converted value
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Address converted = converter.map(toAdd, Address.class);

        // need to make the connection to the user manually (but first make sure user exists)
        if(!userRepo.existsById(toAdd.getUserId()))
            throw new Exception("User does not exist in database. Aborting operation.");
        converted.setCreatedBy(userRepo.getUserByUserId(toAdd.getUserId()));

        // and return what the repository would normally return
        return converter.map(addressRepo.save(converted), AddressDTO.class);
    }

    /**
     * Returns an address based on the passed address ID
     * @param addressId the id of the address to look up
     * @return a converted address object
     * @throws Exception if the address does not exist
     */
    public AddressDTO getAddressById(long addressId) throws Exception {
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        Optional<Address> relAddress = addressRepo.findById(addressId);
        if(relAddress.isEmpty())
            throw new Exception("Address does not exist in database. Aborting operation.");

        return converter.map(relAddress.get(), AddressDTO.class);
    }

    /**
     * Gets a user's list of addresses associated with their account
     */
    public List<AddressDTO> getAddressesByUser(UUID userId) {
        // first we need to get our relevant user
        Optional<User> relUser = userRepo.findById(userId);
        if(relUser.isEmpty())
            return new ArrayList<>();

        // if the user exists now submit the query
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return addressRepo.findAllByCreatedBy(relUser.get()).stream().map(relAddress -> converter.map(relAddress, AddressDTO.class)).toList();
    }

    /**
     * Returns if the address exists in the database
     * @param addressId
     * @return
     */
    public boolean addressExists(long addressId) {
        return addressRepo.existsById(addressId);
    }

    /**
     * Takes in a (mostly empty) address DTO and uses the ID entry to delete it from
     * the database.
     * @param addressId determines the address to delete
     */
    public void deleteAddressById(long addressId) {
        addressRepo.deleteById(addressId);
    }
}
