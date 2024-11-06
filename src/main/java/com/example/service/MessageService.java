package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class MessageService {
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository AccountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    public boolean createMessage(Message message){
        Account foundAccount = accountRepository.getById(message.getPostedBy()); 
        if(foundAccount==null && message.getMessageText().length()>0 && message.getMessageText().length()<=255){
            messageRepository.save(message);
            return true;
        }
        return false;
    }
}
