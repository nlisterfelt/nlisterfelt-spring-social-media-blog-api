package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException{
        accountService.login(account.getUsername(), account.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        boolean successful = messageService.createMessage(message);
        if(successful==true){
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findAllMessages());
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
       return ResponseEntity.status(HttpStatus.OK).body(messageService.findMessageById(messageId));
    }

    @GetMapping("account/{acccountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesbyUserId(@PathVariable int accountId){
        List<Message> userMessages = messageService.getMessagesByAccountId(accountId);
        if(userMessages==null) return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(userMessages);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int messageId){
        messageService.deleteMessage(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Message> updateMessage(@PathVariable int messageId, @RequestParam String messageText){
        Message updatedMessage = messageService.updateMessage(messageId, messageText);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
    }
}
