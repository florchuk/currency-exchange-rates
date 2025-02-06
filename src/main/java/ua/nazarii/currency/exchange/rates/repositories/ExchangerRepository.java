package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.domains.ArchiveRateDomain;
import ua.nazarii.currency.exchange.rates.domains.ExchangerDomain;
import ua.nazarii.currency.exchange.rates.domains.RateDomain;
import ua.nazarii.currency.exchange.rates.entities.ExchangerEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExchangerRepository {
    List<ExchangerDomain<RateDomain>> findAllExchangerRateDomains();

    List<ExchangerDomain<ArchiveRateDomain>> findExchangerArchiveRateDomainsByRateIdsAndCreatedAtBetween(
            List<Integer> rateIds,
            LocalDateTime startAt,
            LocalDateTime endAt
    );

    Optional<ExchangerEntity> findExchangerEntityById(Integer id);
}
