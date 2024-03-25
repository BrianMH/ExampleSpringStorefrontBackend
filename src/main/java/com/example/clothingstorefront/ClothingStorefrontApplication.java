package com.example.clothingstorefront;

import com.example.clothingstorefront.dto.*;
import com.example.clothingstorefront.model.Role;
import com.example.clothingstorefront.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * We don't use the security built into Spring for our application, so we intentionally exclude it
 * from our imports so that we don't get the default authentication parameters.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ClothingStorefrontApplication implements CommandLineRunner {
    // static values for generation
    private static final int MAX_CREATED_MSGS = 11;
    private static final int MAX_CREATED_USERS = 4;
    private static final int MAX_CREATED_CATS = 10;
    private static final int MAX_CREATED_PRODUCTS = 10;
    private static final int MAX_NOTIFS_CREATED = 3;
    private static final int CAROUSEL_LENGTH = 3;
    private static final List<String> imList = List.of("2850487/pexels-photo-2850487.jpeg",
                                                       "251454/pexels-photo-251454.jpeg",
                                                        "8532616/pexels-photo-8532616.jpeg",
                                                        "7671168/pexels-photo-7671168.jpeg",
                                                        "4614242/pexels-photo-4614242.jpeg");

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductCategoryService prodCatService;

    @Autowired
    private ProductService prodService;

    @Autowired
    private NotificationService notifService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // We can use this method to pre-populate our database
    @Override
    public void run(String... args) {
//        initializeMessages();
//        initializeUsers();
//        initializeProductCategories();
//        initializeProductsUnderCategories();
//        initializeNotifications();
    }

    public String generateRandomImageSource() {
        Random random = new Random();
        return imList.get(random.nextInt(imList.size()));
    }

    public void initializeNotifications() {
        Optional<UserDTO> relUser = userService.findByEmail("admin@test.com");
        if(relUser.isEmpty())
            throw new RuntimeException("Error occurred during notification creation.");

        prodCatService.getAllCategories().forEach( curProdCat -> {
            NotificationDTO curNotif = new NotificationDTO();
            curNotif.setNotifyBy(relUser.get());
            curNotif.setCategory(curProdCat);

            try {
                notifService.addNotification(curNotif);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void initializeProductsUnderCategories() {
        // we first need the names of all categories that we have
        List<ProductCategoryDTO> catList = prodCatService.getAllCategories();
        Random random = new Random();

        // create random entries under every category
        catList.forEach(cat -> {
            ProductDTO curProduct;
            List<String> imList;

            for(int prodInd = 0; prodInd < MAX_CREATED_PRODUCTS; prodInd++) {
                curProduct = new ProductDTO();
                curProduct.setDescription(String.format("Descr of Item %d -> %d", cat.getId(), prodInd));
                curProduct.setName(String.format("Item %d -> %d", cat.getId(), prodInd));

                imList = new ArrayList<>();
                for(int imInd = 0; imInd < CAROUSEL_LENGTH; imInd++) {
                    imList.add(generateRandomImageSource());
                }

                curProduct.setImageCollection(imList);
                curProduct.setPriceInCents(random.nextInt(99999));
                curProduct.setOutOfStock(false);
                curProduct.setProdCategory(cat);

                // and then persist to database
                prodService.addProduct(curProduct);
            }
        });
    }

    public void initializeProductCategories() {
        ProductCategoryDTO curProdCat;
        for(int catInd = 0; catInd < MAX_CREATED_CATS; catInd++) {
            curProdCat = new ProductCategoryDTO();
            curProdCat.setDescription(String.format("A description corresponding to category #%d", catInd));
            curProdCat.setImageRef(generateRandomImageSource());
            curProdCat.setTagName(String.format("Category %d", catInd));

            prodCatService.addCategory(curProdCat);
        }
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
        // use our repo to manually add some users
        UserDTO curUser;
        for(int userInd = 0; userInd < MAX_CREATED_USERS; userInd++) {
            curUser = new UserDTO();
            curUser.setEmail(String.format("example+%d@gmail.com", userInd));
            curUser.setUsername(String.format("user%d", userInd));
            curUser.setPassword(passwordEncoder.encode("password"));
            curUser.setScreenName("Example User");
            curUser.setRole(Role.ROLE_USER.toString());

            // persist
            userService.addUser(curUser);
        }

        // And we go ahead and create an admin account out of convenience
        curUser = new UserDTO();
        curUser.setEmail("admin@test.com");
        curUser.setUsername("admin");
        curUser.setPassword(passwordEncoder.encode("password"));
        curUser.setScreenName("Admin User");
        curUser.setRole(Role.ROLE_ADMIN.toString());
        userService.addUser(curUser);
    }

    public static void main(String[] args) {
        SpringApplication.run(ClothingStorefrontApplication.class, args);
    }

}
