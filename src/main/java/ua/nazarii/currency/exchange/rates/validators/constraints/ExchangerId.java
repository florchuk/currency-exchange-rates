package ua.nazarii.currency.exchange.rates.validators.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ua.nazarii.currency.exchange.rates.validators.validators.ExchangerIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExchangerIdValidator.class)
public @interface ExchangerId {
    String message() default "{ua.nazarii.currency.exchange.rates.constraints.ExchangerId.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
