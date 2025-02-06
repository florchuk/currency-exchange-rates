package ua.nazarii.currency.exchange.rates.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import ua.nazarii.currency.exchange.rates.dto.RateEntityDto;
import ua.nazarii.currency.exchange.rates.services.RateService;

import java.lang.reflect.Field;
import java.util.*;

@RequestMapping(
        path = "/api/rates",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PutMapping(path = {"", "/"})
    public ResponseEntity<List<RateEntityDto>> saveRateEntityDtos(@Valid @RequestBody List<RateEntityDto> rateDtos) {
        this.rateService.saveRateEntityDtos(rateDtos);

        return ResponseEntity.ok(rateDtos);
    }

    @ExceptionHandler(
            exception = HandlerMethodValidationException.class,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<Integer, Map<String, List<String>>>> handleHandlerMethodValidationException(
            HandlerMethodValidationException e
    ) throws NoSuchFieldException {
        Map<Integer, Map<String, List<String>>> response = new HashMap<>();

        for (ParameterValidationResult parameterValidationResult : e.getParameterValidationResults()) {
            Integer index = parameterValidationResult.getContainerIndex();

            for (MessageSourceResolvable messageSourceResolvable : parameterValidationResult.getResolvableErrors()) {
                FieldError fieldError = (FieldError) messageSourceResolvable;
                String key = fieldError.getField();
                String message = Objects.requireNonNullElse(fieldError.getDefaultMessage(), "");
                Field field = RateEntityDto.class.getDeclaredField(key);

                if(field.isAnnotationPresent(JsonProperty.class)) {
                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);

                    if (!jsonProperty.value().isEmpty()) {
                        key = jsonProperty.value();
                    }
                }

                if (response.containsKey(index)) {
                    if (response.get(index).containsKey(key)) {
                        response
                                .get(index)
                                .get(key)
                                .add(message);
                    } else {
                        List<String> messages = new ArrayList<>(){{
                            add(message);
                        }};

                        response.get(index).put(key, messages);
                    }
                } else {
                    Map<String, List<String>> errors = new HashMap<>();
                    List<String> messages = new ArrayList<>(){{
                        add(message);
                    }};

                    errors.put(key, messages);

                    response.put(index, errors);
                }
            }
        }

        return ResponseEntity.badRequest().body(response);
    }
}
