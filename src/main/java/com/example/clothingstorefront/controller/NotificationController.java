package com.example.clothingstorefront.controller;

import com.example.clothingstorefront.dto.NotificationDTO;
import com.example.clothingstorefront.dto.ResultDTO;
import com.example.clothingstorefront.service.NotificationService;
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

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(notifSvc.getAllNotificationsByUser(userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultDTO> patchNotificationObject(@RequestBody NotificationDTO modifyVals, @PathVariable long id) {
        try {
            notifSvc.patchNotification(modifyVals, id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, "Patched"));
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, e.getMessage()));
        }
    }

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
