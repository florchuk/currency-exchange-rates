package ua.nazarii.currency.exchange.rates.domains;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ExchangerDomain<T> {
    private Integer id;

    private List<T> rateDomains;

    private String name;

    private String nameUk;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ExchangerDomain(
            Integer id,
            List<T> rateDomains,
            String name,
            String nameUk,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.rateDomains = rateDomains;
        this.name = name;
        this.nameUk = nameUk;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return this.id;
    }

    public List<T> getRateDomains() {
        return this.rateDomains;
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

    public void setRateDomains(List<T> rateDomains) {
        this.rateDomains = rateDomains;
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

        ExchangerDomain<?> exchangerDomain = (ExchangerDomain<?>) object;

        return Objects.equals(this.getId(), exchangerDomain.getId())
                && Objects.equals(this.getRateDomains(), exchangerDomain.getRateDomains())
                && Objects.equals(this.getName(), exchangerDomain.getName())
                && Objects.equals(this.getNameUk(), exchangerDomain.getNameUk())
                && Objects.equals(this.getCreatedAt(), exchangerDomain.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), exchangerDomain.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getRateDomains() != null ? this.getRateDomains().hashCode() : 0);
        result = multiplier * result + (this.getName() != null ? this.getName().hashCode() : 0);
        result = multiplier * result + (this.getNameUk() != null ? this.getNameUk().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
