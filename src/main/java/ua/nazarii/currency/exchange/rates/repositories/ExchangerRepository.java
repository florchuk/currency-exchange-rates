package ua.nazarii.currency.exchange.rates.repositories;

import ua.nazarii.currency.exchange.rates.domains.ExchangerDomain;
import ua.nazarii.currency.exchange.rates.entities.ExchangerEntity;

import java.util.List;
import java.util.Optional;

public interface ExchangerRepository {
    List<ExchangerDomain> findAllDomains();

    List<ExchangerEntity> findAllEntities();

    Optional<ExchangerEntity> findFirstEntityById(Integer id);
}
