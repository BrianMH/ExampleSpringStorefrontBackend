package com.example.clothingstorefront.service;

import com.example.clothingstorefront.dto.NotificationDTO;
import com.example.clothingstorefront.model.Notification;
import com.example.clothingstorefront.model.ProdCategory;
import com.example.clothingstorefront.model.User;
import com.example.clothingstorefront.repository.NotificationRepository;
import com.example.clothingstorefront.repository.ProdCategoryRepository;
import com.example.clothingstorefront.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notifRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProdCategoryRepository prodCatRepo;

    /**
     * Patches a notification object with the required information (requires the DTO to include the object ID along
     * with any patch information necessary)
     * @param patch
     * @throws Exception
     */
    public void patchNotification(NotificationDTO patch, long modifyId) throws Exception {
        // first make sure our element exists
        Optional<Notification> toModify = notifRepo.findById(modifyId);
        if(toModify.isEmpty())
            throw new Exception("Failed to find requested notification object. Aborting...");
        Notification relChangeObject = toModify.get();

        if(!(patch.getEnabled() == null))
            relChangeObject.setEnabled(patch.getEnabled());
        if(!(patch.getCategory() == null)) {
            Optional<ProdCategory> relCat = prodCatRepo.findById(patch.getCategory().getId());
            if(relCat.isEmpty())
                throw new Exception("Failed to find requested category object. Aborting...");
            relChangeObject.setCategory(relCat.get());
        }
        if(!(patch.getNotifyBy() == null)) {
            Optional<User> relUser = userRepo.findById(patch.getNotifyBy().getUserId());
            if(relUser.isEmpty())
                throw new Exception("Failed to find requested user object. Aborting...");
            relChangeObject.setNotifyBy(relUser.get());
        }

        // and then persist changes
        notifRepo.save(relChangeObject);
    }

    /**
     * Deletes a given notification based on a passed id
     * @param id
     * @throws Exception
     */
    public void deleteNotificationById(long id) throws Exception {
        if(!notifRepo.existsById(id))
            throw new Exception("Notification did not exist in database");

        notifRepo.deleteById(id);
    }

    /**
     * Returns a list of notifications associated with a user
     * @param userId the user to get the notifications for
     * @return
     */
    public List<NotificationDTO> getAllNotificationsByUser(UUID userId) {
        List<Notification> toRet = notifRepo.findAllByNotifyBy_UserId(userId);

        // and convert
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return toRet.stream().map(notification -> converter.map(notification, NotificationDTO.class)).toList();
    }

    /**
     * Adding a notification requires a bit more care due to the way in which the value is returned
     *
     * @param toAdd the DTO representing the notification to add
     * @return the representation of the added notification in DTO form
     */
    public NotificationDTO addNotification(NotificationDTO toAdd) throws Exception {
        // first construct our normal Notification
        Notification convertedAddVal = new Notification();

        // find our required user
        Optional<User> relUser = userRepo.findById(toAdd.getNotifyBy().getUserId());

        // and our product category
        Optional<ProdCategory> relCategory = prodCatRepo.findById(toAdd.getCategory().getId());

        // and make sure both exist before continuing
        if(relUser.isEmpty() || relCategory.isEmpty()) {
            throw new Exception("Failed to find associated category or user...");
        }
        convertedAddVal.setCategory(relCategory.get());
        convertedAddVal.setNotifyBy(relUser.get());

        // and then persist the values
        Notification savedValue = notifRepo.save(convertedAddVal);
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return converter.map(savedValue, NotificationDTO.class);
    }
}
