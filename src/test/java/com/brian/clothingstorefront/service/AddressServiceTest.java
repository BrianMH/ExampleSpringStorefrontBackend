package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.AddressDTO;
import com.brian.clothingstorefront.model.Address;
import com.brian.clothingstorefront.model.Role;
import com.brian.clothingstorefront.model.User;
import com.brian.clothingstorefront.repository.AddressRepository;
import com.brian.clothingstorefront.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AddressServiceTest {

    @TestConfiguration
    static class AddressServiceTestConfiguration {

        @Bean
        public AddressService addressService() {
            return new AddressService();
        }
    }

    @Autowired
    private AddressService addressService;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // here we can "assume" the repository functions properly and mock its outputs
        UUID userUUID = new UUID(0, 0);
        Optional<User> user = Optional.of(new User(userUUID, "email", "username",
                "", "", Role.ROLE_USER, "", null,
                null, null, null, null, null));

        Mockito.when(userRepository.findById(userUUID)).thenReturn(user);

        Address onlyAddress = new Address(0, "testName", "", "",
                "", "", "", user.get(), "");
        Mockito.when(addressRepository.findAllByCreatedBy(user.get())).thenReturn(List.of(onlyAddress));
    }

    @Test
    public void testGetByUser() {
        UUID userUUID = new UUID(0, 0);
        List<AddressDTO> found = addressService.getAddressesByUser(userUUID);

        Assert.assertTrue("Only 1 address must be found.", found.size() == 1);
        Assert.assertTrue("Found improper user transformation.", found.getFirst().getFullName().equals("testName"));
    }
}
