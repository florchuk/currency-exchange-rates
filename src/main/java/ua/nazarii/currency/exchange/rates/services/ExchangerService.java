package ua.nazarii.currency.exchange.rates.services;

import org.springframework.stereotype.Service;
import ua.nazarii.currency.exchange.rates.domains.ArchiveRateDomain;
import ua.nazarii.currency.exchange.rates.domains.ExchangerDomain;
import ua.nazarii.currency.exchange.rates.domains.RateDomain;
import ua.nazarii.currency.exchange.rates.dto.*;
import ua.nazarii.currency.exchange.rates.entities.ExchangerEntity;
import ua.nazarii.currency.exchange.rates.repositories.ExchangerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangerService {
    private final ExchangerRepository exchangerRepository;

    public ExchangerService(ExchangerRepository exchangerRepository) {
        this.exchangerRepository = exchangerRepository;
    }

    public List<ExchangerDomainDto<RateDomainDto>> findAllExchangerRateDomainDtos() {
        return this.exchangerRepository
                .findAllExchangerRateDomains()
                .stream()
                .map(
                        (ExchangerDomain<RateDomain> exchangerDomain) -> new ExchangerDomainDto<>(
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

    public List<ExchangerDomainDto<ArchiveRateDomainDto>> findExchangerArchiveRateDomainDtosByRateIdsAndCreatedAtBetween(
            List<Integer> rateIds,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        return this.exchangerRepository
                .findExchangerArchiveRateDomainsByRateIdsAndCreatedAtBetween(
                        rateIds,
                        startAt,
                        endAt
                )
                .stream()
                .map(
                        (ExchangerDomain<ArchiveRateDomain> exchangerDomain) -> new ExchangerDomainDto<>(
                                exchangerDomain.getId(),
                                exchangerDomain
                                        .getRateDomains()
                                        .stream()
                                        .map(
                                                (ArchiveRateDomain archiveRateDomain) -> new ArchiveRateDomainDto(
                                                        archiveRateDomain.getId(),
                                                        archiveRateDomain.getUnit(),
                                                        new CurrencyDomainDto(
                                                                archiveRateDomain.getUnitCurrencyDomain().getAlphabeticCode(),
                                                                archiveRateDomain.getUnitCurrencyDomain().getDecimalPlace(),
                                                                archiveRateDomain.getUnitCurrencyDomain().getName(),
                                                                archiveRateDomain.getUnitCurrencyDomain().getNameUk(),
                                                                archiveRateDomain.getUnitCurrencyDomain().getCreatedAt(),
                                                                archiveRateDomain.getUnitCurrencyDomain().getUpdatedAt()
                                                        ),
                                                        new CurrencyDomainDto(
                                                                archiveRateDomain.getRateCurrencyDomain().getAlphabeticCode(),
                                                                archiveRateDomain.getRateCurrencyDomain().getDecimalPlace(),
                                                                archiveRateDomain.getRateCurrencyDomain().getName(),
                                                                archiveRateDomain.getRateCurrencyDomain().getNameUk(),
                                                                archiveRateDomain.getRateCurrencyDomain().getCreatedAt(),
                                                                archiveRateDomain.getRateCurrencyDomain().getUpdatedAt()
                                                        ),
                                                        archiveRateDomain.getBuyRate(),
                                                        archiveRateDomain.getSaleRate(),
                                                        archiveRateDomain.getCreatedAt(),
                                                        archiveRateDomain.getUpdatedAt()
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

    public Optional<ExchangerEntityDto> findExchangerEntityDtoById(Integer id) {
        return this.exchangerRepository
                .findExchangerEntityById(id)
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
