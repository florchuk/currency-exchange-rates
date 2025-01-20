package ua.nazarii.currency.exchange.rates.validators.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.nazarii.currency.exchange.rates.services.CurrencyService;
import ua.nazarii.currency.exchange.rates.validators.constraints.CurrencyAlphabeticCode;

public class CurrencyAlphabeticCodeValidator implements ConstraintValidator<CurrencyAlphabeticCode, String> {
    private final CurrencyService currencyService;

    public CurrencyAlphabeticCodeValidator(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // This validator is not responsible for validation of null values.
        if (value == null) {
            return true;
        }

        return this.currencyService.findFirstEntityDtoByCurrencyAlphabeticCode(value).isPresent();
    }
}
