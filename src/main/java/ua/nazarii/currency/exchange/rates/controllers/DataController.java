package ua.nazarii.currency.exchange.rates.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nazarii.currency.exchange.rates.dto.CurrencyEntityDto;
import ua.nazarii.currency.exchange.rates.dto.ExchangerEntityDto;
import ua.nazarii.currency.exchange.rates.services.CurrencyService;
import ua.nazarii.currency.exchange.rates.services.ExchangerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(
        path = "/api/data",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class DataController {
    private final ExchangerService exchangerService;
    private final CurrencyService currencyService;

    public DataController(ExchangerService exchangerService, CurrencyService currencyService) {
        this.exchangerService = exchangerService;
        this.currencyService = currencyService;
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<Map<String, List<?>>> findAllEntityDtos() {
        List<ExchangerEntityDto> exchangerEntityDtos = this.exchangerService.findAllEntityDtos();
        List<CurrencyEntityDto> currencyEntityDtos = this.currencyService.findAllEntityDtos();

        return ResponseEntity.ok(
                new HashMap<>(){{
                    put("exchangers", exchangerEntityDtos);
                    put("currencies", currencyEntityDtos);
                }}
        );
    }
}
