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
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
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
        return messageRepository.findByMessageId(messageId);
    }
    public List<Message> getMessagesByAccountId (int accountId)  {
        List<Message> userMessages = messageRepository.findByPostedBy(accountId);
        return userMessages;
    }
    public boolean deleteMessage (int messageId){
        Message foundMessage = messageRepository.findByMessageId(messageId);
        if(foundMessage!=null){
            messageRepository.deleteByMessageId(messageId);
            return true;
        } else {
            return false;
        }
    }
    public Message updateMessage(int messageId, String messageText){
        Message updatedMessage = messageRepository.findByMessageId(messageId);
        if(messageText.length()>0 && messageText.length()<=255){
            updatedMessage.setMessageText(messageText);
        }
        messageRepository.save(updatedMessage);
        return updatedMessage;
    }
}
