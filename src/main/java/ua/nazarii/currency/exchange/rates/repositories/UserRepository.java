package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByUsername(String username);
}
