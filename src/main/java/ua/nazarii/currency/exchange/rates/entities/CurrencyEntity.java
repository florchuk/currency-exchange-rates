package ua.nazarii.currency.exchange.rates.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class CurrencyEntity {
    private String alphabeticCode;

    private Integer decimalPlace;

    private String name;

    private String nameUk;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CurrencyEntity(
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

        CurrencyEntity currencyEntity = (CurrencyEntity) object;

        return Objects.equals(this.getAlphabeticCode(), currencyEntity.getAlphabeticCode())
                && Objects.equals(this.getDecimalPlace(), currencyEntity.getDecimalPlace())
                && Objects.equals(this.getName(), currencyEntity.getName())
                && Objects.equals(this.getNameUk(), currencyEntity.getNameUk())
                && Objects.equals(this.getCreatedAt(), currencyEntity.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), currencyEntity.getUpdatedAt());
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
