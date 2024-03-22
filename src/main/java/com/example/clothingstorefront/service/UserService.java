package com.example.clothingstorefront.service;

import com.example.clothingstorefront.dto.UserDTO;
import com.example.clothingstorefront.model.User;
import com.example.clothingstorefront.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public UserDTO getByUUID(UUID uuid) {
        User relUser = userRepo.getUserByUserId(uuid);

        // convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return converter.map(relUser, UserDTO.class);
    }

    public Optional<UserDTO> findByUUID(UUID uuid) {
        Optional<User> relUser = userRepo.findById(uuid);

        // convert and return
        if(relUser.isEmpty())
            return Optional.empty();

        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return Optional.of(converter.map(relUser.get(), UserDTO.class));
    }

    public Optional<UserDTO> findByEmail(String email) {
        Optional<User> relUser = userRepo.findByEmail(email);

        // convert and return
        if(relUser.isEmpty())
            return Optional.empty();

        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return Optional.of(converter.map(relUser.get(), UserDTO.class));
    }

    public List<UserDTO> findUsersGivenQuery(String query) {
        List<User> relUser = userRepo.findUsersByQuery(query);

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return relUser.stream().map((user -> converter.map(user, UserDTO.class))).toList();
    }

    public UserDTO addUser(UserDTO inUser) {
        // first convert to User
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        User convertedInput = converter.map(inUser, User.class);
        System.out.println(convertedInput);

        // persist and return DTO
        User savedUser = userRepo.save(convertedInput);
        System.out.println(savedUser);
        return converter.map(savedUser, UserDTO.class);
    }
}
