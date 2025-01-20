package ua.nazarii.currency.exchange.rates.validators.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.nazarii.currency.exchange.rates.validators.validators.CurrencyAlphabeticCodeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CurrencyAlphabeticCodeValidator.class)
public @interface CurrencyAlphabeticCode {
    String message() default "{ua.nazarii.currency.exchange.rates.constraints.CurrencyAlphabeticCode.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
