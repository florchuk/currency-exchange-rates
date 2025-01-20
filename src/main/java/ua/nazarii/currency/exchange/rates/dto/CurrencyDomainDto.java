package ua.nazarii.currency.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class CurrencyDomainDto {
    @JsonProperty(value = "alphabetic_code")
    private String alphabeticCode;

    @JsonProperty(value = "decimal_place")
    private Integer decimalPlace;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "name_uk")
    private String nameUk;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    public CurrencyDomainDto(
            String alphabeticCode,
            Integer decimalPlace,
            String name,
            String nameUk,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.alphabeticCode = alphabeticCode;
        this.decimalPlace = decimalPlace;
        this.name = name;
        this.nameUk = nameUk;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getAlphabeticCode() {
        return this.alphabeticCode;
    }

    public Integer getDecimalPlace() {
        return this.decimalPlace;
    }

    public String getName() {
        return this.name;
    }

    public String getNameUk() {
        return this.nameUk;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setAlphabeticCode(String alphabeticCode) {
        this.alphabeticCode = alphabeticCode;
    }

    public void setDecimalPlace(Integer decimalPlace) {
        this.decimalPlace = decimalPlace;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameUk(String nameUk) {
        this.nameUk = nameUk;
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

        CurrencyDomainDto currencyDomainDto = (CurrencyDomainDto) object;

        return Objects.equals(this.getAlphabeticCode(), currencyDomainDto.getAlphabeticCode())
                && Objects.equals(this.getDecimalPlace(), currencyDomainDto.getDecimalPlace())
                && Objects.equals(this.getName(), currencyDomainDto.getName())
                && Objects.equals(this.getNameUk(), currencyDomainDto.getNameUk())
                && Objects.equals(this.getCreatedAt(), currencyDomainDto.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), currencyDomainDto.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getAlphabeticCode() != null ? this.getAlphabeticCode().hashCode() : 0);
        result = multiplier * result + (this.getDecimalPlace() != null ? this.getDecimalPlace().hashCode() : 0);
        result = multiplier * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = multiplier * result + (this.getNameUk() != null ? this.getNameUk().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
