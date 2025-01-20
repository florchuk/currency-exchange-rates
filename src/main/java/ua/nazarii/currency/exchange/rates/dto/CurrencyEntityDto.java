package ua.nazarii.currency.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class CurrencyEntityDto {
    @JsonProperty(value = "alphabetic_code")
    private final String alphabeticCode;

    @JsonProperty(value = "decimal_place")
    private final Integer decimalPlace;

    @JsonProperty(value = "name")
    private final String name;

    @JsonProperty(value = "name_uk")
    private final String nameUk;

    @JsonProperty(value = "created_at")
    private final LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private final LocalDateTime updatedAt;

    public CurrencyEntityDto(
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        CurrencyEntityDto currencyEntityDto = (CurrencyEntityDto) object;

        return Objects.equals(this.getAlphabeticCode(), currencyEntityDto.getAlphabeticCode())
                && Objects.equals(this.getDecimalPlace(), currencyEntityDto.getDecimalPlace())
                && Objects.equals(this.getName(), currencyEntityDto.getName())
                && Objects.equals(this.getNameUk(), currencyEntityDto.getNameUk())
                && Objects.equals(this.getCreatedAt(), currencyEntityDto.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), currencyEntityDto.getUpdatedAt());
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
