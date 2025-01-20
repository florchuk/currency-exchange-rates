package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.CurrencyEntity;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {
    List<CurrencyEntity> findAllEntities();

    Optional<CurrencyEntity> findFirstEntityByCurrencyAlphabeticCode(String currencyAlphabeticCode);
}
