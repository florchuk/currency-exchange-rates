package ua.nazarii.currency.exchange.rates.domains;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArchiveRateDomain {
    private Long id;

    private Integer unit;

    private CurrencyDomain unitCurrencyDomain;

    private CurrencyDomain rateCurrencyDomain;

    private Double buyRate;

    private Double saleRate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ArchiveRateDomain(
            Long id,
            Integer unit,
            CurrencyDomain unitCurrencyDomain,
            CurrencyDomain rateCurrencyDomain,
            Double buyRate,
            Double saleRate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.unit = unit;
        this.unitCurrencyDomain = unitCurrencyDomain;
        this.rateCurrencyDomain = rateCurrencyDomain;
        this.buyRate = buyRate;
        this.saleRate = saleRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getUnit() {
        return this.unit;
    }

    public CurrencyDomain getUnitCurrencyDomain() {
        return this.unitCurrencyDomain;
    }

    public CurrencyDomain getRateCurrencyDomain() {
        return this.rateCurrencyDomain;
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

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setUnitCurrencyDomain(CurrencyDomain unitCurrencyDomain) {
        this.unitCurrencyDomain = unitCurrencyDomain;
    }

    public void setRateCurrencyDomain(CurrencyDomain rateCurrencyDomain) {
        this.rateCurrencyDomain = rateCurrencyDomain;
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

        ArchiveRateDomain archiveRateDomain = (ArchiveRateDomain) object;

        return Objects.equals(this.getId(), archiveRateDomain.getId())
                && Objects.equals(this.getUnit(), archiveRateDomain.getUnit())
                && Objects.equals(this.getUnitCurrencyDomain(), archiveRateDomain.getUnitCurrencyDomain())
                && Objects.equals(this.getRateCurrencyDomain(), archiveRateDomain.getRateCurrencyDomain())
                && Objects.equals(this.getBuyRate(), archiveRateDomain.getBuyRate())
                && Objects.equals(this.getSaleRate(), archiveRateDomain.getSaleRate())
                && Objects.equals(this.getCreatedAt(), archiveRateDomain.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), archiveRateDomain.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getUnit() != null ? this.getUnit().hashCode() : 0);
        result = multiplier * result
                + (this.getUnitCurrencyDomain() != null ? this.getUnitCurrencyDomain().hashCode() : 0);
        result = multiplier * result
                + (this.getRateCurrencyDomain() != null ? this.getRateCurrencyDomain().hashCode() : 0);
        result = multiplier * result + (this.getBuyRate() != null ? this.getBuyRate().hashCode() : 0);
        result = multiplier * result + (this.getSaleRate() != null ? this.getSaleRate().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
