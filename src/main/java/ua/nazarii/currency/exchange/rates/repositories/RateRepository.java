package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.RateEntity;

import java.util.Optional;

public interface RateRepository {
    Optional<RateEntity> findFirstEntityByExchangerIdAndUnitCurrencyAlphabeticCodeAndRateCurrencyAlphabeticCode(
            Integer exchangerId,
            String unitCurrencyAlphabeticCode,
            String rateCurrencyAlphabeticCode
    );

    int insert(RateEntity rateEntity);

    int update(RateEntity rateEntity);
}
