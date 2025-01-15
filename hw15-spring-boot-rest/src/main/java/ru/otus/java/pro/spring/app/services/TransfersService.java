package ru.otus.java.pro.spring.app.services;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.spring.app.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.spring.app.entities.Transfer;

public interface TransfersService {
    Optional<Transfer> getTransferById(String id, String clientId);

    List<Transfer> getAllTransfers(String clientId);

    @Transactional
    void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq);
}
