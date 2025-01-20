package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.ArchiveRateEntity;

import java.util.Optional;

public interface ArchiveRateRepository {
    Optional<ArchiveRateEntity> findFirstEntityByRateIdOrderByCreatedAtDesc(Integer rateId);

    int insert(ArchiveRateEntity archiveRateEntity);

    int update(ArchiveRateEntity archiveRateEntity);
}
