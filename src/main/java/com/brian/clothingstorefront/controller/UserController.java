package com.brian.clothingstorefront.controller;

import com.brian.clothingstorefront.dto.ResultDTO;
import com.brian.clothingstorefront.dto.UserDTO;
import com.brian.clothingstorefront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Adds a user to the database given the passed DTO. If an error is thrown, returns a BAD_REQUEST value along with
     * the ResultDTO corresponding to the failure.
     * @param inUser
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserDTO inUser) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(inUser));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO(false, e.getMessage()));
        }
    }

    /**
     * Gets a user given their UUID or returns a NOT_FOUND along with the ResultDTO representing the failure, otherwise.
     * @param inUser
     * @return
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUser(@PathVariable("uuid") UUID inUser) {
        // We can fetch particular users by UUID
        Optional<UserDTO> received = userService.findByUUID(inUser);
        if(received.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find given user."));

        return ResponseEntity.status(HttpStatus.OK).body(received.get());
    }

    /**
     * Patches the user object given a passedDTO object representing the desired changes
     * @param adjustedUser
     * @return
     */
    @PatchMapping("/update")
    public ResponseEntity<ResultDTO> updateUser(@RequestBody UserDTO adjustedUser) {
        // We go ahead and patch our specified user with any of the requested changes passed onto UserDTO
        if(!userService.userExists(adjustedUser.getUserId()))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find given user."));

        // otherwise patch
        userService.updateUser(adjustedUser.getUserId(), adjustedUser);
        return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, "User object modified"));
    }

    /**
     * Returns a list of all users in the database (which can be empty)
     * @param query
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam("query") String query) {
        // For this, we need to receive all users given some search query (empty being generic)
        List<UserDTO> received = userService.findUsersGivenQuery(query);

        // Then wrap this payload
        return ResponseEntity.status(HttpStatus.OK).body(received);
    }

    /**
     * Returns the number of pages that would be needed to represent the list of users that satisfy a given query
     * but returned in sets of pageSize.
     * @param pageSize
     * @param query
     * @return
     */
    @GetMapping("/paged/{pageSize}")
    public ResponseEntity<Long> getNumPages(@PathVariable int pageSize, @RequestParam String query) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getNumPages(pageSize, query));
    }

    /**
     * Returns the actual pages associated with users that satisfy a given query
     * @param page
     * @param pageSize
     * @param query
     * @return
     */
    @GetMapping("/paged/{pageSize}/{page}")
    public ResponseEntity<List<UserDTO>> getPagedUsersWithQuery(@PathVariable int page, @PathVariable int pageSize, @RequestParam String query) {
        // throw an error with problematic inputs
        if(page < 0 || pageSize <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());

        // like before we can just return the paged values
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOffsetPagedUsersGivenQuery(page, pageSize, query));
    }

    /**
     * Returns a user given a known e-mail (which is only used for login authentication)
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUser(@PathVariable("email") String email) {
        // fetch the user by email now
        Optional<UserDTO> received = userService.findByEmail(email);
        if(received.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Failed to find given user."));

        return ResponseEntity.status(HttpStatus.OK).body(received.get());
    }
}
