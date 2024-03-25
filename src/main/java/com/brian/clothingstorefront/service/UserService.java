package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.UserDTO;
import com.brian.clothingstorefront.model.Role;
import com.brian.clothingstorefront.repository.UserRepository;
import com.brian.clothingstorefront.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    public List<UserDTO> getOffsetPagedUsersGivenQuery(int pageNumber, int pageSize, String query) {
        Page<User> page = userRepo.findAllByQueryWithPages(query, PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(message -> converter.map(message, UserDTO.class)).toList();
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

        // default role will always be USER for everyone
        if(convertedInput.getRole() == null)
            convertedInput.setRole(Role.ROLE_USER);

        // persist and return DTO
        User savedUser = userRepo.save(convertedInput);
        return converter.map(savedUser, UserDTO.class);
    }

    public Long getNumPages(long pageSize, String query) {
        return (long)Math.ceil(userRepo.findNumPagesWithQuery(query)/(double)pageSize);
    }

    public boolean userExists(UUID reqUser) {
        return userRepo.existsById(reqUser);
    }

    public void updateUser(UUID reqUser, UserDTO adjustedUser) {
        // we can get our user first
        Optional<User> userHolder = userRepo.findById(reqUser);
        if(userHolder.isEmpty())
            return;
        User toModify = userHolder.get();

        // first perform changes to object
        if(!(adjustedUser.getAvatarRef() == null))
            toModify.setAvatarRef(adjustedUser.getAvatarRef());
        if(!(adjustedUser.getEmail() == null))
            toModify.setEmail(adjustedUser.getEmail());
        if(!(adjustedUser.getPassword() == null))
            toModify.setPassword(adjustedUser.getPassword());
        if(!(adjustedUser.getScreenName() == null))
            toModify.setScreenName(adjustedUser.getScreenName());

        // and re-persist the object
        userRepo.save(toModify);
    }
}
