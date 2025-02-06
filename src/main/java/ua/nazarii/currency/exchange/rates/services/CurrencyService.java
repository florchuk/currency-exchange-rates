package ua.nazarii.currency.exchange.rates.services;

import org.springframework.stereotype.Service;
import ua.nazarii.currency.exchange.rates.dto.CurrencyEntityDto;
import ua.nazarii.currency.exchange.rates.entities.CurrencyEntity;
import ua.nazarii.currency.exchange.rates.repositories.CurrencyRepository;

import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Optional<CurrencyEntityDto> findCurrencyEntityDtoByAlphabeticCode(String alphabeticCode) {
        return this.currencyRepository
                .findCurrencyEntityByAlphabeticCode(alphabeticCode)
                .map(
                        (CurrencyEntity currencyEntity) -> new CurrencyEntityDto(
                                currencyEntity.getAlphabeticCode(),
                                currencyEntity.getDecimalPlace(),
                                currencyEntity.getName(),
                                currencyEntity.getNameUk(),
                                currencyEntity.getCreatedAt(),
                                currencyEntity.getUpdatedAt()
                        )
                );
    }
}
