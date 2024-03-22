package com.example.clothingstorefront.controller;

import com.example.clothingstorefront.dto.ResultDTO;
import com.example.clothingstorefront.dto.UserDTO;
import com.example.clothingstorefront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO inUser) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(inUser));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUser(@PathVariable("uuid") UUID inUser) {
        // We can fetch particular users by UUID
        Optional<UserDTO> received = userService.findByUUID(inUser);
        if(received.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find given user."));

        return ResponseEntity.status(HttpStatus.OK).body(received.get());
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam("query") String query) {
        // For this, we need to receive all users given some search query (empty being generic)
        List<UserDTO> received = userService.findUsersGivenQuery(query);

        // Then wrap this payload
        return ResponseEntity.status(HttpStatus.OK).body(received);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUser(@PathVariable("email") String email) {
        // fetch the user by email now
        Optional<UserDTO> received = userService.findByEmail(email);
        if(received.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find given user."));

        return ResponseEntity.status(HttpStatus.OK).body(received.get());
    }
}
