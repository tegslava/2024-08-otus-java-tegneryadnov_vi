package ru.otus.java.pro.spring.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.spring.app.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.spring.app.entities.Account;
import ru.otus.java.pro.spring.app.entities.Transfer;
import ru.otus.java.pro.spring.app.exceptions_handling.*;
import ru.otus.java.pro.spring.app.repositories.TransfersRepository;

@Service
@RequiredArgsConstructor
public class TransfersServiceImp implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final AccountsServiceImp accountsService;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientIdOrTargetClientId(clientId, clientId);
    }

    @Transactional
    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        validateExecuteTransferDtoRq(executeTransferDtoRq);
        Account sourceAccount = accountsService
                .getAccountByAccountNumber(executeTransferDtoRq.sourceAccount(), clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Счет отправителя не найден"));
        Account targetAccount = accountsService
                .getAccountByAccountNumber(executeTransferDtoRq.targetAccount(), executeTransferDtoRq.targetClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Счет получателя не найден"));
        validateBusinessLogic(new TransferParamsDto(sourceAccount, targetAccount, executeTransferDtoRq.amount()));
        sourceAccount.setBalance(sourceAccount.getBalance() - executeTransferDtoRq.amount());
        targetAccount.setBalance(targetAccount.getBalance() + executeTransferDtoRq.amount());
        transfersRepository.save(new Transfer(
                UUID.randomUUID().toString(),
                clientId,
                executeTransferDtoRq.targetClientId(),
                executeTransferDtoRq.sourceAccount(),
                executeTransferDtoRq.targetAccount(),
                executeTransferDtoRq.message(),
                executeTransferDtoRq.amount()));
    }

    private void validateBusinessLogic(TransferParamsDto transferParamsDto) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (transferParamsDto.sourceAccount().isBlocked()) {
            errors.add(new ValidationFieldError("sourceAccount", "Счет отправителя заблокирован"));
        }
        if (transferParamsDto.targetAccount().isBlocked()) {
            errors.add(new ValidationFieldError("targetAccount", "Счет получателя заблокирован"));
        }
        if (transferParamsDto.sourceAccount().getBalance() - transferParamsDto.amount() < 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода больше остатка на счете отправителя"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationBusinessLogicException("BUSINESS_LOGIC_ERROR", "Ошибка в параметрах перевода", errors);
        }
    }

    private void validateExecuteTransferDtoRq(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.sourceAccount().length() != 12) {
            errors.add(new ValidationFieldError(
                    "sourceAccount", "Длина поля счет отправителя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.targetAccount().length() != 12) {
            errors.add(new ValidationFieldError(
                    "targetAccount", "Длина поля счет получателя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.amount() <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(
                    "EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }
}
