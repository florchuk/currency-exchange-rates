package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.RateEntity;

import java.util.Optional;

public interface RateRepository {
    Optional<RateEntity> findRateEntityByExchangerIdAndUnitCurrencyAlphabeticCodeAndRateCurrencyAlphabeticCode(
            Integer exchangerId,
            String unitCurrencyAlphabeticCode,
            String rateCurrencyAlphabeticCode
    );

    int insertRateEntity(RateEntity rateEntity);

    int updateRateEntity(RateEntity rateEntity);
}
