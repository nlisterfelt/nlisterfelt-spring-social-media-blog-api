package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    
    public MessageService(MessageRepository messageRepository, AccountRepository AccountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    public boolean createMessage(Message message){
        Optional<Account> foundAccount = accountRepository.findByAccountId(message.getPostedBy());
        if(!foundAccount.isPresent()) return false;
        if(message.getMessageText().length()>0 && message.getMessageText().length()<=255){
            messageRepository.save(message);
            return true;
        }
        return false;
    }
    public List<Message> findAllMessages(){
        return messageRepository.findAll();
    }
    public Message findMessageById(int messageId){
        Optional<Message> foundMessage = messageRepository.findByMessageId(messageId);
        if(foundMessage.isPresent()) return foundMessage.get();
        return null;
    }
    public List<Message> getMessagesByAccountId (int accountId)  {
        Optional<List<Message>> userMessages = messageRepository.findByPostedBy(accountId);
        if(userMessages.isPresent()) return userMessages.get();
        return null;
    }
    public boolean deleteMessage (int messageId){
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);
        if(optionalMessage.isPresent()){
            messageRepository.deleteByMessageId(messageId);
            return true;
        } else {
            return false;
        }
    }
    public Message updateMessage(int messageId, String messageText){
        Optional<Message> optionalMessage = messageRepository.findByMessageId(messageId);
        if(optionalMessage.isPresent() && messageText.length()>0 && messageText.length()<=255){
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessageText(messageText);
            messageRepository.save(updatedMessage);
            return updatedMessage;
        }
        return null;
    }
}
