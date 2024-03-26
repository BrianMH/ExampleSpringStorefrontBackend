package com.brian.clothingstorefront.controller;

import com.brian.clothingstorefront.dto.NotificationDTO;
import com.brian.clothingstorefront.service.NotificationService;
import com.brian.clothingstorefront.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notifSvc;

    /**
     * Returns the notifications associated with a given user that has a known UUID (which can be empty)
     * @param userId
     * @return
     */
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(notifSvc.getAllNotificationsByUser(userId));
    }

    /**
     * Patches the notification to change a value that is stored in the database. The Id is passed separately in this
     * function, but could be combined into the DTO in the future.
     * @param modifyVals
     * @param id
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ResultDTO> patchNotificationObject(@RequestBody NotificationDTO modifyVals, @PathVariable long id) {
        try {
            notifSvc.patchNotification(modifyVals, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, "Patched"));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, e.getMessage()));
        }
    }

    /**
     * Deletes a given notification based on the notification's known ID.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultDTO> deleteNotificationById(@PathVariable long id) {
        try {
            notifSvc.deleteNotificationById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, "Deleted"));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, e.getMessage()));
        }
    }


}
