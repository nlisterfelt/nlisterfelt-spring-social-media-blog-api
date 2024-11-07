package com.example.service;

import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public boolean register(Account newAccount){
        Optional<Account> foundAccount = accountRepository.findByUsernameAndPassword(newAccount.getUsername(), newAccount.getPassword());
        if(foundAccount.isPresent()){
            return false;
        }else{
            accountRepository.save(newAccount);
            return true;
        }
    }

    public Account login(String username, String password){
        Optional<Account> foundAccount = accountRepository.findByUsernameAndPassword(username, password);
        if(foundAccount.isPresent()){
            return foundAccount.get();
        } else {
            return null;
        }
    }
}
