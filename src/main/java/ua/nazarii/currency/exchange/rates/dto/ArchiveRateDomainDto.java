package ua.nazarii.currency.exchange.rates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class ArchiveRateDomainDto {
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "unit")
    private Integer unit;

    @JsonProperty(value = "unit_currency")
    private CurrencyDomainDto unitCurrencyDomainDto;

    @JsonProperty(value = "rate_currency")
    private CurrencyDomainDto rateCurrencyDomainDto;

    @JsonProperty(value = "buy_rate")
    private Double buyRate;

    @JsonProperty(value = "sale_rate")
    private Double saleRate;

    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    public ArchiveRateDomainDto(
            Long id,
            Integer unit,
            CurrencyDomainDto unitCurrencyDomainDto,
            CurrencyDomainDto rateCurrencyDomainDto,
            Double buyRate,
            Double saleRate,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.unit = unit;
        this.unitCurrencyDomainDto = unitCurrencyDomainDto;
        this.rateCurrencyDomainDto = rateCurrencyDomainDto;
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

    public CurrencyDomainDto getUnitCurrencyDomainDto() {
        return this.unitCurrencyDomainDto;
    }

    public CurrencyDomainDto getRateCurrencyDomainDto() {
        return this.rateCurrencyDomainDto;
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

    public void setUnitCurrencyDomainDto(CurrencyDomainDto unitCurrencyDomainDto) {
        this.unitCurrencyDomainDto = unitCurrencyDomainDto;
    }

    public void setRateCurrencyDomainDto(CurrencyDomainDto rateCurrencyDomainDto) {
        this.rateCurrencyDomainDto = rateCurrencyDomainDto;
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

        ArchiveRateDomainDto archiveRateDomainDto = (ArchiveRateDomainDto) object;

        return Objects.equals(this.getId(), archiveRateDomainDto.getId())
                && Objects.equals(this.getUnit(), archiveRateDomainDto.getUnit())
                && Objects.equals(this.getUnitCurrencyDomainDto(), archiveRateDomainDto.getUnitCurrencyDomainDto())
                && Objects.equals(this.getRateCurrencyDomainDto(), archiveRateDomainDto.getRateCurrencyDomainDto())
                && Objects.equals(this.getBuyRate(), archiveRateDomainDto.getBuyRate())
                && Objects.equals(this.getSaleRate(), archiveRateDomainDto.getSaleRate())
                && Objects.equals(this.getCreatedAt(), archiveRateDomainDto.getCreatedAt())
                && Objects.equals(this.getUpdatedAt(), archiveRateDomainDto.getUpdatedAt());
    }

    @Override
    public int hashCode() {
        int result = 1;
        int multiplier = 31;

        result = multiplier * result + (this.getId() != null ? this.getId().hashCode() : 0);
        result = multiplier * result + (this.getUnit() != null ? this.getUnit().hashCode() : 0);
        result = multiplier * result
                + (this.getUnitCurrencyDomainDto() != null ? this.getUnitCurrencyDomainDto().hashCode() : 0);
        result = multiplier * result
                + (this.getRateCurrencyDomainDto() != null ? this.getRateCurrencyDomainDto().hashCode() : 0);
        result = multiplier * result + (this.getBuyRate() != null ? this.getBuyRate().hashCode() : 0);
        result = multiplier * result + (this.getSaleRate() != null ? this.getSaleRate().hashCode() : 0);
        result = multiplier * result + (this.getCreatedAt() != null ? this.getCreatedAt().hashCode() : 0);
        result = multiplier * result + (this.getUpdatedAt() != null ? this.getUpdatedAt().hashCode() : 0);

        return result;
    }
}
