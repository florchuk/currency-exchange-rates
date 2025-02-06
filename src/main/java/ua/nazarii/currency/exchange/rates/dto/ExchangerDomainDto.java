package ua.nazarii.currency.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ExchangerDomainDto<T> {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "rates")
    private List<T> rateDomainDtos;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "name_uk")
    private String nameUk;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    public ExchangerDomainDto(
            Integer id,
            List<T> rateDomainDtos,
            String name,
            String nameUk,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.rateDomainDtos = rateDomainDtos;
        this.name = name;
        this.nameUk = nameUk;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return this.id;
    }

    public List<T> getRateDomainDtos() {
        return this.rateDomainDtos;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRateDomainDtos(List<T> rateDomainDtos) {
        this.rateDomainDtos = rateDomainDtos;
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

        ExchangerDomainDto<?> exchangerDomainDto = (ExchangerDomainDto<?>) object;

        return Objects.equals(this.getId(), exchangerDomainDto.getId())
                && Objects.equals(this.getRateDomainDtos(), exchangerDomainDto.getRateDomainDtos())
                && Objects.equals(this.getName(), exchangerDomainDto.getName())
                && Objects.equals(this.getNameUk(), exchangerDomainDto.getNameUk())
                && Objects.equals(this.getCreatedAt(), exchangerDomainDto.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), exchangerDomainDto.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getRateDomainDtos() != null ? this.getRateDomainDtos().hashCode() : 0);
        result = multiplier * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = multiplier * result + (this.getNameUk() != null ? this.getNameUk().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
