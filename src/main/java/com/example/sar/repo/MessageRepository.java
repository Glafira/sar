package com.example.sar.repo;

import com.example.sar.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Long> {
   Messages findMessagesById(Long id);
   List<Messages> findAll();

}
