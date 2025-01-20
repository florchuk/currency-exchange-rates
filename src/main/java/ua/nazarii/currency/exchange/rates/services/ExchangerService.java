package ua.nazarii.currency.exchange.rates.services;

import org.springframework.stereotype.Service;
import ua.nazarii.currency.exchange.rates.domains.ExchangerDomain;
import ua.nazarii.currency.exchange.rates.domains.RateDomain;
import ua.nazarii.currency.exchange.rates.dto.CurrencyDomainDto;
import ua.nazarii.currency.exchange.rates.dto.ExchangerDomainDto;
import ua.nazarii.currency.exchange.rates.dto.ExchangerEntityDto;
import ua.nazarii.currency.exchange.rates.dto.RateDomainDto;
import ua.nazarii.currency.exchange.rates.entities.ExchangerEntity;
import ua.nazarii.currency.exchange.rates.repositories.ExchangerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangerService {
    private final ExchangerRepository exchangerRepository;

    public ExchangerService(ExchangerRepository exchangerRepository) {
        this.exchangerRepository = exchangerRepository;
    }

    public List<ExchangerDomainDto> findAllDomainDtos() {
        return this.exchangerRepository
                .findAllDomains()
                .stream()
                .map(
                        (ExchangerDomain exchangerDomain) -> new ExchangerDomainDto(
                                exchangerDomain.getId(),
                                exchangerDomain
                                        .getRateDomains()
                                        .stream()
                                        .map(
                                                (RateDomain rateDomain) -> new RateDomainDto(
                                                        rateDomain.getId(),
                                                        rateDomain.getUnit(),
                                                        new CurrencyDomainDto(
                                                                rateDomain.getUnitCurrencyDomain().getAlphabeticCode(),
                                                                rateDomain.getUnitCurrencyDomain().getDecimalPlace(),
                                                                rateDomain.getUnitCurrencyDomain().getName(),
                                                                rateDomain.getUnitCurrencyDomain().getNameUk(),
                                                                rateDomain.getUnitCurrencyDomain().getCreatedAt(),
                                                                rateDomain.getUnitCurrencyDomain().getUpdatedAt()
                                                        ),
                                                        new CurrencyDomainDto(
                                                                rateDomain.getRateCurrencyDomain().getAlphabeticCode(),
                                                                rateDomain.getRateCurrencyDomain().getDecimalPlace(),
                                                                rateDomain.getRateCurrencyDomain().getName(),
                                                                rateDomain.getRateCurrencyDomain().getNameUk(),
                                                                rateDomain.getRateCurrencyDomain().getCreatedAt(),
                                                                rateDomain.getRateCurrencyDomain().getUpdatedAt()
                                                        ),
                                                        rateDomain.getBuyRate(),
                                                        rateDomain.getSaleRate(),
                                                        rateDomain.getCreatedAt(),
                                                        rateDomain.getUpdatedAt()
                                                )
                                        )
                                        .toList(),
                                exchangerDomain.getName(),
                                exchangerDomain.getNameUk(),
                                exchangerDomain.getCreatedAt(),
                                exchangerDomain.getUpdatedAt()
                        )
                )
                .toList();
    }

    public List<ExchangerEntityDto> findAllEntityDtos() {
        return this.exchangerRepository
                .findAllEntities()
                .stream()
                .map(
                        (ExchangerEntity exchangerEntity) -> new ExchangerEntityDto(
                                exchangerEntity.getId(),
                                exchangerEntity.getName(),
                                exchangerEntity.getNameUk(),
                                exchangerEntity.getCreatedAt(),
                                exchangerEntity.getUpdatedAt()
                        )
                )
                .toList();
    }

    public Optional<ExchangerEntityDto> findFirstEntityDtoById(Integer id) {
        return this.exchangerRepository
                .findFirstEntityById(id)
                .map(
                        (ExchangerEntity exchangerEntity) -> new ExchangerEntityDto(
                                exchangerEntity.getId(),
                                exchangerEntity.getName(),
                                exchangerEntity.getNameUk(),
                                exchangerEntity.getCreatedAt(),
                                exchangerEntity.getUpdatedAt()
                        )
                );
    }
}
