package com.hanumant.repo;

import com.hanumant.model.SendMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SendMessageRepository extends JpaRepository<SendMessage, Integer> {
    List<SendMessage> findByStatus(String status);
}
