package ua.nazarii.currency.exchange.rates.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.nazarii.currency.exchange.rates.domains.ArchiveRateDomain;
import ua.nazarii.currency.exchange.rates.domains.CurrencyDomain;
import ua.nazarii.currency.exchange.rates.domains.ExchangerDomain;
import ua.nazarii.currency.exchange.rates.domains.RateDomain;
import ua.nazarii.currency.exchange.rates.dto.RateDomainDto;
import ua.nazarii.currency.exchange.rates.entities.ExchangerEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class PostgreSqlExchangerRepository implements ExchangerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgreSqlExchangerRepository(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<ExchangerDomain<RateDomain>> findAllExchangerRateDomains() {
        Map<Integer, ExchangerDomain<RateDomain>> exchangerDomains = new HashMap<>();

        this.jdbcTemplate.query("""
                        SELECT e.id AS e_id,
                            e.name AS e_name,
                            e.name_uk AS e_name_uk,
                            e.created_at AS e_created_at,
                            e.updated_at AS e_updated_at,
                            r.id AS r_id,
                            r.unit AS r_unit,
                            r.buy_rate AS r_buy_rate,
                            r.sale_rate AS r_sale_rate,
                            r.created_at AS r_created_at,
                            r.updated_at AS r_updated_at,
                            c_unit.alphabetic_code AS c_unit_alphabetic_code,
                            c_unit.decimal_place AS c_unit_decimal_place,
                            c_unit.name AS c_unit_name,
                            c_unit.name_uk AS c_unit_name_uk,
                            c_unit.created_at AS c_unit_created_at,
                            c_unit.updated_at AS c_unit_updated_at,
                            c_rate.alphabetic_code AS c_rate_alphabetic_code,
                            c_rate.decimal_place AS c_rate_decimal_place,
                            c_rate.name AS c_rate_name,
                            c_rate.name_uk AS c_rate_name_uk,
                            c_rate.created_at AS c_rate_created_at,
                            c_rate.updated_at AS c_rate_updated_at
                        FROM exchangers e
                        INNER JOIN rates r ON (
                            e.id = r.exchanger_id
                        )
                        INNER JOIN currencies c_unit ON (
                            r.unit_currency_alphabetic_code = c_unit.alphabetic_code
                        )
                        INNER JOIN currencies c_rate ON (
                            r.rate_currency_alphabetic_code = c_rate.alphabetic_code
                        )
                        ORDER BY e.id ASC,
                            r.unit_currency_alphabetic_code ASC,
                            r.rate_currency_alphabetic_code ASC;
                        """,
                (ResultSet resultSet) -> {
                    int exchangerId = resultSet.getInt("e_id");

                    RateDomain rateDomain = new RateDomain(
                            resultSet.getInt("r_id"),
                            resultSet.getInt("r_unit"),
                            new CurrencyDomain(
                                    resultSet.getString("c_unit_alphabetic_code"),
                                    resultSet.getInt("c_unit_decimal_place"),
                                    resultSet.getString("c_unit_name"),
                                    resultSet.getString("c_unit_name_uk"),
                                    resultSet.getTimestamp("c_unit_created_at").toLocalDateTime(),
                                    resultSet.getTimestamp("c_unit_updated_at").toLocalDateTime()
                            ),
                            new CurrencyDomain(
                                    resultSet.getString("c_rate_alphabetic_code"),
                                    resultSet.getInt("c_rate_decimal_place"),
                                    resultSet.getString("c_rate_name"),
                                    resultSet.getString("c_rate_name_uk"),
                                    resultSet.getTimestamp("c_rate_created_at").toLocalDateTime(),
                                    resultSet.getTimestamp("c_rate_updated_at").toLocalDateTime()
                            ),
                            resultSet.getDouble("r_buy_rate"),
                            resultSet.getDouble("r_sale_rate"),
                            resultSet.getTimestamp("r_created_at").toLocalDateTime(),
                            resultSet.getTimestamp("r_updated_at").toLocalDateTime()
                    );

                    if (exchangerDomains.containsKey(exchangerId)) {
                        exchangerDomains
                                .get(exchangerId)
                                .getRateDomains()
                                .add(rateDomain);
                    } else {
                        exchangerDomains.put(
                                exchangerId,
                                new ExchangerDomain<>(
                                        exchangerId,
                                        new ArrayList<>(){{
                                            add(rateDomain);
                                        }},
                                        resultSet.getString("e_name"),
                                        resultSet.getString("e_name_uk"),
                                        resultSet.getTimestamp("e_created_at").toLocalDateTime(),
                                        resultSet.getTimestamp("e_updated_at").toLocalDateTime()
                                )
                        );
                    }
                }
        );

        return exchangerDomains
                .values()
                .stream()
                .toList();
    }

    @Override
    public List<ExchangerDomain<ArchiveRateDomain>> findExchangerArchiveRateDomainsByRateIdsAndCreatedAtBetween(
            List<Integer> rateIds,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        Map<Integer, ExchangerDomain<ArchiveRateDomain>> exchangerDomains = new HashMap<>();

        this.namedParameterJdbcTemplate.query("""
                        SELECT e.id AS e_id,
                            e.name AS e_name,
                            e.name_uk AS e_name_uk,
                            e.created_at AS e_created_at,
                            e.updated_at AS e_updated_at,
                            ar.id AS ar_id,
                            ar.unit AS ar_unit,
                            ar.buy_rate AS ar_buy_rate,
                            ar.sale_rate AS ar_sale_rate,
                            ar.created_at AS ar_created_at,
                            ar.updated_at AS ar_updated_at,
                            c_unit.alphabetic_code AS c_unit_alphabetic_code,
                            c_unit.decimal_place AS c_unit_decimal_place,
                            c_unit.name AS c_unit_name,
                            c_unit.name_uk AS c_unit_name_uk,
                            c_unit.created_at AS c_unit_created_at,
                            c_unit.updated_at AS c_unit_updated_at,
                            c_rate.alphabetic_code AS c_rate_alphabetic_code,
                            c_rate.decimal_place AS c_rate_decimal_place,
                            c_rate.name AS c_rate_name,
                            c_rate.name_uk AS c_rate_name_uk,
                            c_rate.created_at AS c_rate_created_at,
                            c_rate.updated_at AS c_rate_updated_at
                        FROM exchangers e
                        INNER JOIN rates r ON (
                            e.id = r.exchanger_id
                        )
                        INNER JOIN archive_rates ar ON (
                            ar.rate_id = r.id
                        )
                        INNER JOIN currencies c_unit ON (
                            r.unit_currency_alphabetic_code = c_unit.alphabetic_code
                        )
                        INNER JOIN currencies c_rate ON (
                            r.rate_currency_alphabetic_code = c_rate.alphabetic_code
                        )
                        WHERE r.id IN (:rateIds)
                            AND ar.created_at BETWEEN :startAt AND :endAt
                        ORDER BY e.id ASC,
                            ar.created_at ASC;
                        """,
                new MapSqlParameterSource(){{
                    addValue("rateIds", rateIds, Types.INTEGER);
                    addValue("startAt", startAt, Types.TIMESTAMP);
                    addValue("endAt", endAt, Types.TIMESTAMP);
                }},
                (ResultSet resultSet) -> {
                    int exchangerId = resultSet.getInt("e_id");

                    ArchiveRateDomain archiveRateDomain = new ArchiveRateDomain(
                            resultSet.getLong("ar_id"),
                            resultSet.getInt("ar_unit"),
                            new CurrencyDomain(
                                    resultSet.getString("c_unit_alphabetic_code"),
                                    resultSet.getInt("c_unit_decimal_place"),
                                    resultSet.getString("c_unit_name"),
                                    resultSet.getString("c_unit_name_uk"),
                                    resultSet.getTimestamp("c_unit_created_at").toLocalDateTime(),
                                    resultSet.getTimestamp("c_unit_updated_at").toLocalDateTime()
                            ),
                            new CurrencyDomain(
                                    resultSet.getString("c_rate_alphabetic_code"),
                                    resultSet.getInt("c_rate_decimal_place"),
                                    resultSet.getString("c_rate_name"),
                                    resultSet.getString("c_rate_name_uk"),
                                    resultSet.getTimestamp("c_rate_created_at").toLocalDateTime(),
                                    resultSet.getTimestamp("c_rate_updated_at").toLocalDateTime()
                            ),
                            resultSet.getDouble("ar_buy_rate"),
                            resultSet.getDouble("ar_sale_rate"),
                            resultSet.getTimestamp("ar_created_at").toLocalDateTime(),
                            resultSet.getTimestamp("ar_updated_at").toLocalDateTime()
                    );

                    if (exchangerDomains.containsKey(exchangerId)) {
                        exchangerDomains
                                .get(exchangerId)
                                .getRateDomains()
                                .add(archiveRateDomain);
                    } else {
                        exchangerDomains.put(
                                exchangerId,
                                new ExchangerDomain<>(
                                        exchangerId,
                                        new ArrayList<>(){{
                                            add(archiveRateDomain);
                                        }},
                                        resultSet.getString("e_name"),
                                        resultSet.getString("e_name_uk"),
                                        resultSet.getTimestamp("e_created_at").toLocalDateTime(),
                                        resultSet.getTimestamp("e_updated_at").toLocalDateTime()
                                )
                        );
                    }
                }
        );

        return exchangerDomains
                .values()
                .stream()
                .toList();
    }

    @Override
    public Optional<ExchangerEntity> findExchangerEntityById(Integer id) {
        return this.namedParameterJdbcTemplate
                .query(
                        """
                               SELECT e.name AS e_name,
                                    e.name_uk AS e_name_uk,
                                    e.created_at AS e_created_at,
                                    e.updated_at AS e_updated_at
                               FROM exchangers e
                               WHERE e.id = :id
                               LIMIT 1;
                               """,
                        new MapSqlParameterSource(){{
                            addValue("id", id, Types.INTEGER);
                        }},
                        (ResultSet resultSet, int rowNum) -> new ExchangerEntity(
                                id,
                                resultSet.getString("e_name"),
                                resultSet.getString("e_name_uk"),
                                resultSet.getTimestamp("e_created_at").toLocalDateTime(),
                                resultSet.getTimestamp("e_updated_at").toLocalDateTime()
                        )
                )
                .stream()
                .findFirst();
    }
}
