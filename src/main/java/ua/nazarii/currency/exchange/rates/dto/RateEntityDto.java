package ua.nazarii.currency.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import ua.nazarii.currency.exchange.rates.validators.constraints.CurrencyAlphabeticCode;
import ua.nazarii.currency.exchange.rates.validators.constraints.ExchangerId;

import java.time.LocalDateTime;
import java.util.Objects;

public class RateEntityDto {
    @Null
    @JsonProperty(value = "id")
    private Integer id;

    @NotNull
    @Positive
    @ExchangerId
    @JsonProperty(value = "exchanger_id")
    private Integer exchangerId;

    @NotNull
    @Positive
    @JsonProperty(value = "unit")
    private Integer unit;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    @CurrencyAlphabeticCode
    @JsonProperty(value = "unit_currency_alphabetic_code")
    private String unitCurrencyAlphabeticCode;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[A-Z]{3}$")
    @CurrencyAlphabeticCode
    @JsonProperty(value = "rate_currency_alphabetic_code")
    private String rateCurrencyAlphabeticCode;

    @NotNull
    @Positive
    @JsonProperty(value = "buy_rate")
    private Double buyRate;

    @NotNull
    @Positive
    @JsonProperty(value = "sale_rate")
    private Double saleRate;

    @NotNull
    @PastOrPresent
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @PastOrPresent
    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    public RateEntityDto(
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

        RateEntityDto rateEntityDto = (RateEntityDto) object;

        return Objects.equals(this.getId(), rateEntityDto.getId())
                && Objects.equals(this.getExchangerId(), rateEntityDto.getExchangerId())
                && Objects.equals(this.getUnit(), rateEntityDto.getUnit())
                && Objects.equals(this.getUnitCurrencyAlphabeticCode(), rateEntityDto.getUnitCurrencyAlphabeticCode())
                && Objects.equals(this.getRateCurrencyAlphabeticCode(), rateEntityDto.getRateCurrencyAlphabeticCode())
                && Objects.equals(this.getBuyRate(), rateEntityDto.getBuyRate())
                && Objects.equals(this.getSaleRate(), rateEntityDto.getSaleRate())
                && Objects.equals(this.getCreatedAt(), rateEntityDto.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), rateEntityDto.getUpdatedAt());
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
