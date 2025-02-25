package ru.otus.java.pro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}
