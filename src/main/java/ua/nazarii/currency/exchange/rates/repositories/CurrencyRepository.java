package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.CurrencyEntity;

import java.util.Optional;

public interface CurrencyRepository {
    Optional<CurrencyEntity> findCurrencyEntityByAlphabeticCode(String alphabeticCode);
}
