package ua.nazarii.currency.exchange.rates.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nazarii.currency.exchange.rates.dto.ExchangerDomainDto;
import ua.nazarii.currency.exchange.rates.services.ExchangerService;

import java.util.List;

@RequestMapping(
        path = "/api/exchangers",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class ExchangerController {
    private final ExchangerService exchangerService;

    public ExchangerController(ExchangerService exchangerService) {
        this.exchangerService = exchangerService;
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<ExchangerDomainDto>> findAllDomainDtos() {
        return ResponseEntity.ok(this.exchangerService.findAllDomainDtos());
    }
}
