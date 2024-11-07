package com.example.controller;

import java.util.HashMap;
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
    @Autowired
    MessageService messageService;
    @Autowired
    AccountService accountService;

    public SocialMediaController(MessageService messageService, AccountService accountService){
        this.messageService = messageService;
        this.accountService = accountService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        boolean successful = accountService.register(account);
        if(successful==true){
            return ResponseEntity.status(HttpStatus.OK).body(account);
        } else{
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PostMapping("login")
    public ResponseEntity<Account> login(@RequestBody Account account) throws AuthenticationException{
        Account foundAccount = accountService.login(account.getUsername(), account.getPassword());
        if(foundAccount!=null){
            return ResponseEntity.status(HttpStatus.OK).body(foundAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        boolean status = messageService.createMessage(message);
        if(status){
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
        Message foundMessage = messageService.findMessageById(messageId);
        return ResponseEntity.status(HttpStatus.OK).body(foundMessage);
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesbyAccountId(@PathVariable int accountId){
        List<Message> userMessages = messageService.getMessagesByAccountId(accountId);
        if(userMessages!=null) return ResponseEntity.status(HttpStatus.OK).body(userMessages);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        boolean success = messageService.deleteMessage(messageId);
        if(success) return ResponseEntity.status(HttpStatus.OK).body(1);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody HashMap data){
        String newMessageText = (String) data.get("messageText");
        Message updatedMessage = messageService.updateMessage(messageId,newMessageText);
        if(updatedMessage!=null) return ResponseEntity.status(HttpStatus.OK).body(1);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
