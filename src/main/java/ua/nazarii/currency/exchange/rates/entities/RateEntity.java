package ua.nazarii.currency.exchange.rates.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class RateEntity {
    private Integer id;

    private Integer exchangerId;

    private Integer unit;

    private String unitCurrencyAlphabeticCode;

    private String rateCurrencyAlphabeticCode;

    private Double buyRate;

    private Double saleRate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public RateEntity(
        Integer id,
        Integer exchangerId,
        Integer unit,
        String unitCurrencyAlphabeticCode,
        String rateCurrencyAlphabeticCode,
        Double buyRate,
        Double saleRate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
    ) {
        this.id = id;
        this.exchangerId = exchangerId;
        this.unit = unit;
        this.unitCurrencyAlphabeticCode = unitCurrencyAlphabeticCode;
        this.rateCurrencyAlphabeticCode = rateCurrencyAlphabeticCode;
        this.buyRate = buyRate;
        this.saleRate = saleRate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getExchangerId() {
        return this.exchangerId;
    }

    public Integer getUnit() {
        return this.unit;
    }

    public String getUnitCurrencyAlphabeticCode() {
        return this.unitCurrencyAlphabeticCode;
    }

    public String getRateCurrencyAlphabeticCode() {
        return this.rateCurrencyAlphabeticCode;
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

    public void setExchangerId(Integer exchangerId) {
        this.exchangerId = exchangerId;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public void setUnitCurrencyAlphabeticCode(String unitCurrencyAlphabeticCode) {
        this.unitCurrencyAlphabeticCode = unitCurrencyAlphabeticCode;
    }

    public void setRateCurrencyAlphabeticCode(String rateCurrencyAlphabeticCode) {
        this.rateCurrencyAlphabeticCode = rateCurrencyAlphabeticCode;
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

        RateEntity rateEntity = (RateEntity) object;

        return Objects.equals(this.getId(), rateEntity.getId())
                && Objects.equals(this.getExchangerId(), rateEntity.getExchangerId())
                && Objects.equals(this.getUnit(), rateEntity.getUnit())
                && Objects.equals(this.getUnitCurrencyAlphabeticCode(), rateEntity.getUnitCurrencyAlphabeticCode())
                && Objects.equals(this.getRateCurrencyAlphabeticCode(), rateEntity.getRateCurrencyAlphabeticCode())
                && Objects.equals(this.getBuyRate(), rateEntity.getBuyRate())
                && Objects.equals(this.getSaleRate(), rateEntity.getSaleRate())
                && Objects.equals(this.getCreatedAt(), rateEntity.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), rateEntity.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getExchangerId() != null ? this.getExchangerId().hashCode() : 0);
        result = multiplier * result + (this.getUnit() != null ? this.getUnit().hashCode() : 0);
        result = multiplier * result +
                (this.getUnitCurrencyAlphabeticCode() != null ? this.getUnitCurrencyAlphabeticCode().hashCode() : 0);
        result = multiplier * result +
                (this.getRateCurrencyAlphabeticCode() != null ? this.getRateCurrencyAlphabeticCode().hashCode() : 0);
        result = multiplier * result + (this.getBuyRate() != null ? this.getBuyRate().hashCode() : 0);
        result = multiplier * result + (this.getSaleRate() != null ? this.getSaleRate().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
