package ua.nazarii.currency.exchange.rates.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.nazarii.currency.exchange.rates.dto.ArchiveRateDomainDto;
import ua.nazarii.currency.exchange.rates.dto.ExchangerDomainDto;
import ua.nazarii.currency.exchange.rates.services.ExchangerService;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping(
        path = "/api/exchanger-archive-rates",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
public class ExchangerArchiveRateController {
    private final ExchangerService exchangerService;

    public ExchangerArchiveRateController(ExchangerService exchangerService) {
        this.exchangerService = exchangerService;
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<ExchangerDomainDto<ArchiveRateDomainDto>>> findExchangerArchiveRateDomainDtosByRateIdsAndCreatedAtBetween(
            @RequestParam(name = "rate_ids") List<Integer> rateIds,
            @RequestParam(name = "start_at") LocalDateTime startAt,
            @RequestParam(name = "end_at") LocalDateTime endAt
    ) {
        return ResponseEntity.ok(
                this.exchangerService.findExchangerArchiveRateDomainDtosByRateIdsAndCreatedAtBetween(
                        rateIds,
                        startAt,
                        endAt
                )
        );
    }
}
