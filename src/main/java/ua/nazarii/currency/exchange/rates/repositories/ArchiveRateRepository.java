package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.entities.ArchiveRateEntity;

import java.util.Optional;

public interface ArchiveRateRepository {
    Optional<ArchiveRateEntity> findFirstArchiveRateEntityByRateIdOrderByCreatedAtDesc(Integer rateId);

    int insertArchiveRateEntity(ArchiveRateEntity archiveRateEntity);

    int updateArchiveRateEntity(ArchiveRateEntity archiveRateEntity);
}
