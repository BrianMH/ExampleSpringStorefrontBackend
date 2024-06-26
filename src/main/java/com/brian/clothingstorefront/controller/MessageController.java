package com.brian.clothingstorefront.controller;

import com.brian.clothingstorefront.dto.MessageDTO;
import com.brian.clothingstorefront.dto.ShortMessageDTO;
import com.brian.clothingstorefront.service.MessageService;
import com.brian.clothingstorefront.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService msgService;

    /**
     * Creates a new message based on a passed messageDTO input body
     * @param recMsg
     * @return
     */
    @PostMapping(value = "/new_message")
    public ResponseEntity<MessageDTO> createNewMessage(@RequestBody MessageDTO recMsg) {
        // we can just go ahead and add it as this is by default a new message without an ID or date parameter
        MessageDTO resMsg = msgService.addMessage(recMsg);

        return ResponseEntity.status(HttpStatus.CREATED).body(resMsg);
    }

    /**
     * Deletes a given message by its unique ID.
     * @param remId
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResultDTO> deleteMessageById(@PathVariable("id")long remId) {
        // with deletion we should make sure the id actually exists beforehand
        if(msgService.getMessageById(remId).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Invalid ID"));

        // otherwise return a typical no content on successful deletion
        return ResponseEntity.status(HttpStatus.OK).body(new ResultDTO(true, null));
    }

    /**
     * Gets a specific message and returns it based on its id. If not found, then returns a 404 along with a ResultDTO
     * indicating the operation failure.
     * @param remId
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable("id")long remId) {
        if(msgService.getMessageById(remId).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO(false, "Invalid ID"));

        // otherwise return a typical no content on successful deletion
        return ResponseEntity.status(HttpStatus.OK).body(msgService.getMessageById(remId));
    }

    /**
     * Returns a list of all messages in the database (which can be empty)
     * @return
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        // in this particular request we can just return everything
        return ResponseEntity.status(HttpStatus.OK).body(msgService.getAllMessages());
    }

    /**
     * Paged entries represent shorthand message DTO's being transferred. Whole content can be read manually by
     * accessing the {id} get request.
     * @param page the page to get
     * @param pageSize the size of the page to return
     * @return a list representing the desired page and short-hand messages
     */
    @GetMapping(value = "/paged/{pageSize}/{page}")
    public ResponseEntity<List<ShortMessageDTO>> getPagedShortMessages(@PathVariable int page, @PathVariable int pageSize, @RequestParam String query) {
        // throw an error with problematic inputs
        if(page < 0 || pageSize <= 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());

        // like before we can just return the paged values
        return ResponseEntity.status(HttpStatus.OK).body(msgService.getOffsetPagedBriefMessages(page, pageSize, query));
    }

    /**
     * Takes in a page size and query and returns the number of pages that could possibly be generated based on those
     * two input parameters in the pageable route
     * @param pageSize
     * @param query
     * @return
     */
    @GetMapping(value = "/paged/{pageSize}")
    public ResponseEntity<Long> getNumPages(@PathVariable int pageSize, @RequestParam String query) {
        return ResponseEntity.status(HttpStatus.OK).body(msgService.getNumPages(pageSize, query));
    }
}
