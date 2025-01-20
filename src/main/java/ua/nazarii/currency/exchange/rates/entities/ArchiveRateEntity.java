package ua.nazarii.currency.exchange.rates.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArchiveRateEntity {
    private Long id;

    private Integer rateId;

    private Integer unit;

    private Double buyRate;

    private Double saleRate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ArchiveRateEntity(
        Long id,
        Integer rateId,
        Integer unit,
        Double buyRate,
        Double saleRate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.rateId = rateId;
        this.unit = unit;
        this.buyRate = buyRate;
        this.saleRate = saleRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getRateId() {
        return this.rateId;
    }

    public Integer getUnit() {
        return this.unit;
    }

    public Double getBuyRate() {
        return this.buyRate;
    }

    public Double getSaleRate() {
        return this.saleRate;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setBuyRate(Double buyRate) {
        this.buyRate = buyRate;
    }

    public void setSaleRate(Double saleRate) {
        this.saleRate = saleRate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        ArchiveRateEntity archiveRateEntity = (ArchiveRateEntity) object;

        return Objects.equals(this.getId(), archiveRateEntity.getId())
                && Objects.equals(this.getRateId(), archiveRateEntity.getRateId())
                && Objects.equals(this.getUnit(), archiveRateEntity.getUnit())
                && Objects.equals(this.getBuyRate(), archiveRateEntity.getBuyRate())
                && Objects.equals(this.getSaleRate(), archiveRateEntity.getSaleRate())
                && Objects.equals(this.getCreatedAt(), archiveRateEntity.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), archiveRateEntity.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getRateId() != null ? this.getRateId().hashCode() : 0);
        result = multiplier * result + (this.getUnit() != null ? this.getUnit().hashCode() : 0);
        result = multiplier * result + (this.getBuyRate() != null ? this.getBuyRate().hashCode() : 0);
        result = multiplier * result + (this.getSaleRate() != null ? this.getSaleRate().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
