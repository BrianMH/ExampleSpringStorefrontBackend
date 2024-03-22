package com.example.clothingstorefront;

import com.example.clothingstorefront.dto.MessageDTO;
import com.example.clothingstorefront.dto.UserDTO;
import com.example.clothingstorefront.model.Role;
import com.example.clothingstorefront.service.MessageService;
import com.example.clothingstorefront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * We don't use the security built into Spring for our application, so we intentionally exclude it
 * from our imports so that we don't get the default authentication parameters.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ClothingStorefrontApplication implements CommandLineRunner {
    private static final int MAX_CREATED_MSGS = 11;
    private static final int MAX_CREATED_USERS = 4;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    // We can use this method to pre-populate our database
    @Override
    public void run(String... args) {
        initializeMessages();
        initializeUsers();
    }

    public void initializeMessages() {
        // use our repo to manually add some messages to retrieve
        MessageDTO curMsg;
        for(int msgInd = 0; msgInd < MAX_CREATED_MSGS; msgInd++) {
            curMsg = new MessageDTO();
            curMsg.setName(String.format("User %d", msgInd));
            curMsg.setSubject(String.format("Example Subject %d", msgInd));
            curMsg.setEmail(String.format("example+%d@gmail.com", msgInd));
            curMsg.setMessageContent(String.format("Content%d", msgInd));

            // persist
            messageService.addMessage(curMsg);
        }
    }

    public void initializeUsers() {
        // initialize our bcrypt encoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);

        // use our repo to manually add some users
        UserDTO curUser;
        for(int userInd = 0; userInd < MAX_CREATED_USERS; userInd++) {
            curUser = new UserDTO();
            curUser.setEmail(String.format("example+%d@gmail.com", userInd));
            curUser.setUsername(String.format("user%d", userInd));
            curUser.setPassword(encoder.encode("password"));
            curUser.setScreenName("Example User");
            curUser.setAvatarRef("/defaultPic.png"); // this is hardcoded to the public folder in next. better way?
            curUser.setRole(Role.ROLE_USER.toString());

            // persist
            userService.addUser(curUser);
        }

        // And we go ahead and create an admin account out of convenience
        curUser = new UserDTO();
        curUser.setEmail("admin@test.com");
        curUser.setUsername("admin");
        curUser.setPassword(encoder.encode("password"));
        curUser.setScreenName("Admin User");
        curUser.setAvatarRef("/defaultPic.png");
        curUser.setRole(Role.ROLE_ADMIN.toString());
        userService.addUser(curUser);
    }

    public static void main(String[] args) {
        SpringApplication.run(ClothingStorefrontApplication.class, args);
    }

}
