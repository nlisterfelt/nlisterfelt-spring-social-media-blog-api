package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String>{
    Message findByMessageId(int messageId);

    List<Message> findByPostedBy(int postedBy);

    void deleteByMessageId(int messageId);

}
