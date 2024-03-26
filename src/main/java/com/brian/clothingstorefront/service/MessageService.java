package com.brian.clothingstorefront.service;

import com.brian.clothingstorefront.dto.ShortMessageDTO;
import com.brian.clothingstorefront.dto.MessageDTO;
import com.brian.clothingstorefront.model.Message;
import com.brian.clothingstorefront.repository.MessageRepository;
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

    /**
     * Deletes a given message
     * @param toDelete
     */
    public void deleteMessage(MessageDTO toDelete) {
        // we can use the ID of the message to delete it
        messageRepo.deleteById(toDelete.getId());
    }

    /**
     * Returns a list of all the messages in the database (without pagination)
     * @return list of all messages
     */
    public List<MessageDTO> getAllMessages() {
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return messageRepo.findAll().stream().map(message -> converter.map(message, MessageDTO.class)).toList();
    }

    /**
     * Returns a list of paginated messages in their short format without a query
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public List<ShortMessageDTO> getOffsetPagedBriefMessages(int pageNumber, int pageSize) {
        Page<Message> page = messageRepo.findAllByOrderByDateCreatedDesc(PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(message -> converter.map(message, ShortMessageDTO.class)).toList();
    }

    /**
     * Returns a list of paginated messages in their short format with a query
     * @param pageNumber
     * @param pageSize
     * @param query
     * @return
     */
    public List<ShortMessageDTO> getOffsetPagedBriefMessages(int pageNumber, int pageSize, String query) {
        Page<Message> page = messageRepo.findAllByOrderByDateCreatedDescWithQuery(query, PageRequest.of(pageNumber, pageSize));

        // then convert and return
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return page.stream().map(message -> converter.map(message, ShortMessageDTO.class)).toList();
    }

    /**
     * Returns the number of pages present assuming a given page size
     * @param pageSize
     * @return
     */
    public Long getNumPages(int pageSize) {
        return (long)Math.ceil(messageRepo.count()/(double)pageSize);
    }

    /**
     * Returns the number of pages present assuming a given page size and query
     * @param pageSize
     * @param query
     * @return
     */
    public Long getNumPages(int pageSize, String query) {
        return (long)Math.ceil(messageRepo.findNumPagesWithQuery(query)/(double)pageSize);
    }

    /**
     * Returns a message based on the message Id
     * @param id
     * @return
     */
    public Optional<MessageDTO> getMessageById(long id) {
        ModelMapper converter = new ModelMapper();
        converter.getConfiguration().setMatchingStrategy((MatchingStrategies.STANDARD));

        Optional<Message> msg = messageRepo.findById(id);
        if(msg.isEmpty())
            return Optional.empty();

        return Optional.of(converter.map(msg.get(), MessageDTO.class));
    }
}
