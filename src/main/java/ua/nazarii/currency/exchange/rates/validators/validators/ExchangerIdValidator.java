package ua.nazarii.currency.exchange.rates.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.nazarii.currency.exchange.rates.services.ExchangerService;
import ua.nazarii.currency.exchange.rates.validators.constraints.ExchangerId;

public class ExchangerIdValidator implements ConstraintValidator<ExchangerId, Integer> {
    private final ExchangerService exchangerService;

    public ExchangerIdValidator(ExchangerService exchangerService) {
        this.exchangerService = exchangerService;
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // This validator is not responsible for validation of null values.
        if (value == null) {
            return true;
        }

        return this.exchangerService.findFirstEntityDtoById(value).isPresent();
    }
}
