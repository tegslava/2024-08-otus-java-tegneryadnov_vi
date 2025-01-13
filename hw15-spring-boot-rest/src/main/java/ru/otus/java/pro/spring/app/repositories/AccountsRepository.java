package ru.otus.java.pro.spring.app.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.spring.app.entities.Account;

@Repository
public interface AccountsRepository extends JpaRepository<Account, String> {
    Optional<Account> findByIdAndClientId(String id, String clientId);

    List<Account> findAllByClientId(String clientId);

    Optional<Account> findByAccountNumberAndClientId(String accountNumber, String clientId);
}
