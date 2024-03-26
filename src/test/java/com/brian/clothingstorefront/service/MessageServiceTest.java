package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.MessageDTO;
import com.brian.clothingstorefront.model.Message;
import com.brian.clothingstorefront.repository.MessageRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootTest
public class MessageServiceTest {

    @TestConfiguration
    static class MessageServiceTestConfiguration {

        @Bean
        public MessageService messageService() {
            return new MessageService();
        }
    }

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepo;

    // some static values that were "saved"
    public static Message message1 = new Message(0, "message1", "email1", "subject1", "m1", null);
    public static Message message2 = new Message(1, "message2", "email2", "subject2", "m2", null);
    public static Message message3 = new Message(2, "message3", "email2", "subject3", "m3", null);
    public static List<Message> messageList = List.of(message1,
                                                      message2,
                                                      message3);

    @BeforeEach
    public void setUp() {
        Mockito.when(messageRepo.findAll()).thenReturn(messageList);
    }

    @Test
    public void testGetAllMessages() {
        List<MessageDTO> returnedMessages = messageService.getAllMessages();

        // compare with what we originally had from repo result
        Assert.assertTrue("There must be three elements returned from getAllMessages()", returnedMessages.size() == 3);

        // and since the DTO is 1-1 with the object, we can even go so far as to compare values
        returnedMessages.forEach(messageDTO -> {
            // first get our relevant item from original list
            Message relMsg = messageList.get((int)messageDTO.getId());

            // and assert element-wise on unique elements
            Assert.assertTrue("Message names must match", messageDTO.getName().equals(relMsg.getName()));
            Assert.assertTrue("Message emails must match", messageDTO.getEmail().equals(relMsg.getEmail()));
            Assert.assertTrue("Message subjects must match", messageDTO.getSubject().equals(relMsg.getSubject()));
            Assert.assertTrue("Message contents must match", messageDTO.getMessageContent().equals(relMsg.getMessageContent()));
        });
    }
}
