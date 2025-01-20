package ua.nazarii.currency.exchange.rates.services;

import org.springframework.stereotype.Service;
import ua.nazarii.currency.exchange.rates.dto.CurrencyEntityDto;
import ua.nazarii.currency.exchange.rates.entities.CurrencyEntity;
import ua.nazarii.currency.exchange.rates.repositories.CurrencyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<CurrencyEntityDto> findAllEntityDtos() {
        return this.currencyRepository
                .findAllEntities()
                .stream()
                .map(
                        (CurrencyEntity currencyEntity) -> new CurrencyEntityDto(
                                currencyEntity.getAlphabeticCode(),
                                currencyEntity.getDecimalPlace(),
                                currencyEntity.getName(),
                                currencyEntity.getNameUk(),
                                currencyEntity.getCreatedAt(),
                                currencyEntity.getUpdatedAt()
                        )
                )
                .toList();
    }

    public Optional<CurrencyEntityDto> findFirstEntityDtoByCurrencyAlphabeticCode(String currencyAlphabeticCode) {
        return this.currencyRepository
                .findFirstEntityByCurrencyAlphabeticCode(currencyAlphabeticCode)
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
