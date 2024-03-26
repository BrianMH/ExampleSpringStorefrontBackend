package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.NotificationDTO;
import com.brian.clothingstorefront.dto.ProductCategoryDTO;
import com.brian.clothingstorefront.dto.UserDTO;
import com.brian.clothingstorefront.model.Notification;
import com.brian.clothingstorefront.model.ProdCategory;
import com.brian.clothingstorefront.model.Role;
import com.brian.clothingstorefront.model.User;
import com.brian.clothingstorefront.repository.NotificationRepository;
import com.brian.clothingstorefront.repository.ProdCategoryRepository;
import com.brian.clothingstorefront.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class NotificationServiceTest {

    @TestConfiguration
    static class NotificationServiceTestConfiguration {

        @Bean
        public NotificationService notificationService() {
            return new NotificationService();
        }
    }

    @Autowired
    private NotificationService notificationService;

    // All of theses are dependencies and thus must be mocked
    @MockBean
    private NotificationRepository notifRepo;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private ProdCategoryRepository prodCatRepo;

    // some constants to use since declarations are getting quite big
    private static final UUID USER_UUID = new UUID(0, 0);
    private static final User DEF_USER = new User(USER_UUID, "email", "username",
            "sName", "password", Role.ROLE_USER, "ref", null,
            null, null, null, null, null);

    private static final ProdCategory DEF_CAT = new ProdCategory(0, "tag1", "descr1", "ref", null);

    @BeforeEach
    public void setUp() {
        // first mock the user find op
        Mockito.when(userRepo.findById(USER_UUID)).thenReturn(Optional.of(DEF_USER));

        // and then mock the category find op
        Mockito.when(prodCatRepo.findById(DEF_CAT.getId())).thenReturn(Optional.of(DEF_CAT));

        // and finally mock up the returned object value
        Notification saved = new Notification(0, false, DEF_USER, DEF_CAT);
        Mockito.when(notifRepo.save(saved)).thenReturn(saved);
    }

    @Test
    public void testAddNotification() {
        // first create our notifDTO
        UserDTO emptyDTOWithId = new UserDTO();
        emptyDTOWithId.setUserId(USER_UUID);

        ProductCategoryDTO emptyProdCatDTO = new ProductCategoryDTO();
        emptyProdCatDTO.setId(DEF_CAT.getId());
        NotificationDTO notif = new NotificationDTO(0, emptyDTOWithId, emptyProdCatDTO, false);

        // then use our function
        NotificationDTO retVal = new NotificationDTO();
        try {
            retVal = notificationService.addNotification(notif);
        } catch(Exception e) {
            e.printStackTrace();
            Assert.assertTrue("Assertion should not be thrown by the mocked method...", false);
        }

        // then compare the values to make sure we got the right return
        Assert.assertTrue("Enabled values must match after save.", retVal.getEnabled() == notif.getEnabled());
        Assert.assertTrue("Associated user must match after save", retVal.getNotifyBy().getUserId() == notif.getNotifyBy().getUserId());
        Assert.assertTrue("Associated product must match after save", retVal.getCategory().getId() == notif.getCategory().getId());
    }
}
