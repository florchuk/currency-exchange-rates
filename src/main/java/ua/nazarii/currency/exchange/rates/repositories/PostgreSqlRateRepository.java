package ua.nazarii.currency.exchange.rates.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.nazarii.currency.exchange.rates.entities.RateEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;

@Repository
public class PostgreSqlRateRepository implements RateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgreSqlRateRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<RateEntity> findRateEntityByExchangerIdAndUnitCurrencyAlphabeticCodeAndRateCurrencyAlphabeticCode(
            Integer exchangerId,
            String unitCurrencyAlphabeticCode,
            String rateCurrencyAlphabeticCode
    ) {
        return this.namedParameterJdbcTemplate
                .query(
                        """
                                SELECT r.id AS r_id,
                                    r.unit AS r_unit,
                                    r.buy_rate AS r_buy_rate,
                                    r.sale_rate AS r_sale_rate,
                                    r.created_at AS r_created_at,
                                    r.updated_at AS r_updated_at
                                FROM rates r
                                WHERE r.exchanger_id = :exchangerId
                                    AND r.unit_currency_alphabetic_code = :unitCurrencyAlphabeticCode
                                    AND r.rate_currency_alphabetic_code = :rateCurrencyAlphabeticCode
                                LIMIT 1;
                                """,
                        new MapSqlParameterSource(){{
                            addValue("exchangerId", exchangerId, Types.INTEGER);
                            addValue("unitCurrencyAlphabeticCode", unitCurrencyAlphabeticCode, Types.CHAR);
                            addValue("rateCurrencyAlphabeticCode", rateCurrencyAlphabeticCode, Types.CHAR);
                        }},
                        (ResultSet resultSet, int rowNum) -> new RateEntity(
                                resultSet.getInt("r_id"),
                                exchangerId,
                                resultSet.getInt("r_unit"),
                                unitCurrencyAlphabeticCode,
                                rateCurrencyAlphabeticCode,
                                resultSet.getDouble("r_buy_rate"),
                                resultSet.getDouble("r_sale_rate"),
                                resultSet.getTimestamp("r_created_at").toLocalDateTime(),
                                resultSet.getTimestamp("r_updated_at").toLocalDateTime()
                        )
                )
                .stream()
                .findFirst();
    }

    @Override
    public int insertRateEntity(RateEntity rateEntity) {
        rateEntity.setId(
                this.jdbcTemplate.queryForObject("SELECT NEXTVAL('rates_sequence') AS id;", Integer.class)
        );

        return this.namedParameterJdbcTemplate
                .update(
                        """
                                INSERT INTO rates
                                    (
                                        id,
                                        exchanger_id,
                                        unit,
                                        unit_currency_alphabetic_code,
                                        rate_currency_alphabetic_code,
                                        buy_rate,
                                        sale_rate,
                                        created_at,
                                        updated_at
                                    )
                                VALUES
                                    (
                                        :id,
                                        :exchangerId,
                                        :unit,
                                        :unitCurrencyAlphabeticCode,
                                        :rateCurrencyAlphabeticCode,
                                        :buyRate,
                                        :saleRate,
                                        :createdAt,
                                        :updatedAt
                                    );
                                """,
                        new MapSqlParameterSource(){{
                            addValue("id", rateEntity.getId(), Types.INTEGER);
                            addValue("exchangerId", rateEntity.getExchangerId(), Types.INTEGER);
                            addValue("unit", rateEntity.getUnit(), Types.INTEGER);
                            addValue(
                                    "unitCurrencyAlphabeticCode",
                                    rateEntity.getUnitCurrencyAlphabeticCode(),
                                    Types.CHAR
                            );
                            addValue(
                                    "rateCurrencyAlphabeticCode",
                                    rateEntity.getRateCurrencyAlphabeticCode(),
                                    Types.CHAR
                            );
                            addValue("buyRate", rateEntity.getBuyRate(), Types.DOUBLE);
                            addValue("saleRate", rateEntity.getSaleRate(), Types.DOUBLE);
                            addValue("createdAt", rateEntity.getCreatedAt(), Types.TIMESTAMP);
                            addValue("updatedAt", rateEntity.getUpdatedAt(), Types.TIMESTAMP);
                        }}

                );
    }

    @Override
    public int updateRateEntity(RateEntity rateEntity) {
        return this.namedParameterJdbcTemplate
                .update(
                        """
                                UPDATE rates SET
                                    exchanger_id = :exchangerId,
                                    unit = :unit,
                                    unit_currency_alphabetic_code = :unitCurrencyAlphabeticCode,
                                    rate_currency_alphabetic_code = :rateCurrencyAlphabeticCode,
                                    buy_rate = :buyRate,
                                    sale_rate = :saleRate,
                                    created_at = :createdAt,
                                    updated_at = :updatedAt
                                WHERE id = :id;
                                """,
                        new MapSqlParameterSource(){{
                            addValue("id", rateEntity.getId(), Types.INTEGER);
                            addValue("exchangerId", rateEntity.getExchangerId(), Types.INTEGER);
                            addValue("unit", rateEntity.getUnit(), Types.INTEGER);
                            addValue(
                                    "unitCurrencyAlphabeticCode",
                                    rateEntity.getUnitCurrencyAlphabeticCode(),
                                    Types.CHAR
                            );
                            addValue(
                                    "rateCurrencyAlphabeticCode",
                                    rateEntity.getRateCurrencyAlphabeticCode(),
                                    Types.CHAR
                            );
                            addValue("buyRate", rateEntity.getBuyRate(), Types.DOUBLE);
                            addValue("saleRate", rateEntity.getSaleRate(), Types.DOUBLE);
                            addValue("createdAt", rateEntity.getCreatedAt(), Types.TIMESTAMP);
                            addValue("updatedAt", rateEntity.getUpdatedAt(), Types.TIMESTAMP);
                        }}
                );
    }
}
