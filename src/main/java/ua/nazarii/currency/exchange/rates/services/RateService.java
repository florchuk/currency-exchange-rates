package ua.nazarii.currency.exchange.rates.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nazarii.currency.exchange.rates.dto.RateEntityDto;
import ua.nazarii.currency.exchange.rates.entities.ArchiveRateEntity;
import ua.nazarii.currency.exchange.rates.entities.RateEntity;
import ua.nazarii.currency.exchange.rates.repositories.ArchiveRateRepository;
import ua.nazarii.currency.exchange.rates.repositories.RateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    private final RateRepository rateRepository;
    private final ArchiveRateRepository archiveRateRepository;

    public RateService(RateRepository rateRepository, ArchiveRateRepository archiveRateRepository) {
        this.rateRepository = rateRepository;
        this.archiveRateRepository = archiveRateRepository;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void saveRateEntityDtos(List<RateEntityDto> rateEntityDtos) {
        for (RateEntityDto rateEntityDto : rateEntityDtos) {
            // Mapping the Dto into the Entity.
            RateEntity rateEntity = new RateEntity(
                    rateEntityDto.getId(),
                    rateEntityDto.getExchangerId(),
                    rateEntityDto.getUnit(),
                    rateEntityDto.getUnitCurrencyAlphabeticCode(),
                    rateEntityDto.getRateCurrencyAlphabeticCode(),
                    rateEntityDto.getBuyRate(),
                    rateEntityDto.getSaleRate(),
                    rateEntityDto.getCreatedAt(),
                    rateEntityDto.getUpdatedAt()
            );

            // Mapping the Entity into the Archive Entity.
            ArchiveRateEntity archiveRateEntity = new ArchiveRateEntity(
                    null,
                    rateEntity.getId(),
                    rateEntity.getUnit(),
                    rateEntity.getBuyRate(),
                    rateEntity.getSaleRate(),
                    rateEntity.getCreatedAt(),
                    rateEntity.getUpdatedAt()
            );

            // Searching the Entity in the Repository.
            Optional<RateEntity> optionalCurrentRateEntity = this.rateRepository
                    .findRateEntityByExchangerIdAndUnitCurrencyAlphabeticCodeAndRateCurrencyAlphabeticCode(
                            rateEntity.getExchangerId(),
                            rateEntity.getUnitCurrencyAlphabeticCode(),
                            rateEntity.getRateCurrencyAlphabeticCode()
                    );

            // The Entity was found in the Repository.
            if (optionalCurrentRateEntity.isPresent()) {
                RateEntity currentRateEntity = optionalCurrentRateEntity.get();

                // Updating the Entity.
                currentRateEntity.setUnit(rateEntity.getUnit());
                currentRateEntity.setBuyRate(rateEntity.getBuyRate());
                currentRateEntity.setSaleRate(rateEntity.getSaleRate());
                currentRateEntity.setUpdatedAt(rateEntity.getUpdatedAt());

                // Setting identifier for the Archive Entity.
                archiveRateEntity.setRateId(currentRateEntity.getId());

                // Searching the Archive Entity in the Repository.
                Optional<ArchiveRateEntity> optionalCurrentArchiveRateEntity = this.archiveRateRepository
                        .findFirstArchiveRateEntityByRateIdOrderByCreatedAtDesc(archiveRateEntity.getRateId());

                // The Archive Entity was found in the Repository.
                if (optionalCurrentArchiveRateEntity.isPresent()) {
                    ArchiveRateEntity currentArchiveRateEntity = optionalCurrentArchiveRateEntity.get();

                    if (
                            currentArchiveRateEntity.getUnit().equals(archiveRateEntity.getUnit())
                                && currentArchiveRateEntity.getBuyRate().equals(archiveRateEntity.getBuyRate())
                                && currentArchiveRateEntity.getSaleRate().equals(archiveRateEntity.getSaleRate())
                    ) {
                        // The Archive Entity wasn't changed. Updating the Archive Entity timestamp.
                        currentArchiveRateEntity.setUpdatedAt(archiveRateEntity.getUpdatedAt());

                        // Applying changes for the Archive Entity.
                        this.archiveRateRepository.updateArchiveRateEntity(currentArchiveRateEntity);
                    } else {
                        // The Archive Entity was changed. Adding the Archive Entity into the Repository.
                        this.archiveRateRepository.insertArchiveRateEntity(archiveRateEntity);
                    }
                } else {
                    // The Archive Entity wasn't found in the Repository.
                    // Adding the Archive Entity into the Repository.
                    this.archiveRateRepository.insertArchiveRateEntity(archiveRateEntity);
                }

                // Applying changes for the Entity.
                this.rateRepository.updateRateEntity(currentRateEntity);

                // Updating Dto.
                rateEntityDto.setId(currentRateEntity.getId());
                rateEntityDto.setCreatedAt(currentRateEntity.getCreatedAt());
            } else {
                // The Entity wasn't found in the Repository.
                // Adding the Entity into the Repository.
                this.rateRepository.insertRateEntity(rateEntity);

                // Setting identifier for the Archive Entity.
                archiveRateEntity.setRateId(rateEntity.getId());

                // Adding the Archive Entity into the Repository.
                this.archiveRateRepository.insertArchiveRateEntity(archiveRateEntity);

                // Updating Dto.
                rateEntityDto.setId(rateEntity.getId());
            }
        }
    }
}
