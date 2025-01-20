package ua.nazarii.currency.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExchangerEntityDto {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "name_uk")
    private String nameUk;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    public ExchangerEntityDto(
            Integer id,
            String name,
            String nameUk,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.nameUk = nameUk;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return this.id;
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

        ExchangerEntityDto exchangerEntityDto = (ExchangerEntityDto) object;

        return Objects.equals(this.getId(), exchangerEntityDto.getId())
                && Objects.equals(this.getName(), exchangerEntityDto.getName())
                && Objects.equals(this.getNameUk(), exchangerEntityDto.getNameUk())
                && Objects.equals(this.getCreatedAt(), exchangerEntityDto.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), exchangerEntityDto.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = multiplier * result + (this.getNameUk() != null ? this.getNameUk().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
