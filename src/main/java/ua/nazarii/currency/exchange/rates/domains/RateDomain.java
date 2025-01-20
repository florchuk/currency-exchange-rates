package ua.nazarii.currency.exchange.rates.domains;

import java.time.LocalDateTime;
import java.util.Objects;

public class RateDomain {
    private Integer id;

    private Integer unit;

    private CurrencyDomain unitCurrencyDomain;

    private CurrencyDomain rateCurrencyDomain;

    private Double buyRate;

    private Double saleRate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public RateDomain(
            Integer id,
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

    public Integer getId() {
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

    public void setId(Integer id) {
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

        RateDomain rateDomain = (RateDomain) object;

        return Objects.equals(this.getId(), rateDomain.getId())
                && Objects.equals(this.getUnit(), rateDomain.getUnit())
                && Objects.equals(this.getUnitCurrencyDomain(), rateDomain.getUnitCurrencyDomain())
                && Objects.equals(this.getRateCurrencyDomain(), rateDomain.getRateCurrencyDomain())
                && Objects.equals(this.getBuyRate(), rateDomain.getBuyRate())
                && Objects.equals(this.getSaleRate(), rateDomain.getSaleRate())
                && Objects.equals(this.getCreatedAt(), rateDomain.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), rateDomain.getUpdatedAt());
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
