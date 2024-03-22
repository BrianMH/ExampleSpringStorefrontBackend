package com.example.clothingstorefront.service;

import com.example.clothingstorefront.dto.MessageDTO;
import com.example.clothingstorefront.dto.ShortMessageDTO;
import com.example.clothingstorefront.model.Message;
import com.example.clothingstorefront.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepo;

    /**
     * Adds a message to the repository
     * @param toAdd - The DTO to persist to the database
     * @return the message object persisted to the database
     */
    public MessageDTO addMessage(MessageDTO toAdd) {
        // use mapper to map all elements of DTO to our converted value
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        Message converted = converter.map(toAdd, Message.class);

        // and return what the repository would normally return
        Message persisted = messageRepo.save(converted);
        return converter.map(persisted, MessageDTO.class);
    }

    public void deleteMessage(MessageDTO toDelete) {
        // we can use the ID of the message to delete it
        messageRepo.deleteById(toDelete.getId());
    }

    public List<MessageDTO> getAllMessages() {
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return messageRepo.findAll().stream().map(message -> converter.map(message, MessageDTO.class)).toList();
    }

    public List<ShortMessageDTO> getOffsetPagedBriefMessages(int pageNumber, int pageSize) {
        Page<Message> page = messageRepo.findAllByOrderByDateCreatedDesc(PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(message -> converter.map(message, ShortMessageDTO.class)).toList();
    }

    public List<ShortMessageDTO> getOffsetPagedBriefMessages(int pageNumber, int pageSize, String query) {
        Page<Message> page = messageRepo.findAllByOrderByDateCreatedDescWithQuery(query, PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(message -> converter.map(message, ShortMessageDTO.class)).toList();
    }

    public Long getNumPages(int pageSize) {
        return (long)Math.ceil(messageRepo.count()/(double)pageSize);
    }

    public Long getNumPages(int pageSize, String query) {
        return (long)Math.ceil(messageRepo.findNumPagesWithQuery(query)/(double)pageSize);
    }

    public Optional<MessageDTO> getMessageById(long id) {
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy((MatchingStrategies.STANDARD));

        Optional<Message> msg = messageRepo.findById(id);
        if(msg.isEmpty())
            return Optional.empty();

        return Optional.of(converter.map(msg.get(), MessageDTO.class));
    }
}
