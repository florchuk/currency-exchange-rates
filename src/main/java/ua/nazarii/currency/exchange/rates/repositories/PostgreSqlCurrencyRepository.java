package ua.nazarii.currency.exchange.rates.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.nazarii.currency.exchange.rates.entities.CurrencyEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgreSqlCurrencyRepository implements CurrencyRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgreSqlCurrencyRepository(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<CurrencyEntity> findAllEntities() {
        return this.jdbcTemplate
                .query(
                        """
                                SELECT c.alphabetic_code AS c_alphabetic_code,
                                    c.decimal_place AS c_decimal_place,
                                    c.name AS c_name,
                                    c.name_uk AS c_name_uk,
                                    c.created_at AS c_created_at,
                                    c.updated_at AS c_updated_at
                                FROM currencies c
                                ORDER BY c.alphabetic_code ASC;
                                """,
                        (ResultSet resultSet, int rowNum) -> new CurrencyEntity(
                                resultSet.getString("c_alphabetic_code"),
                                resultSet.getInt("c_decimal_place"),
                                resultSet.getString("c_name"),
                                resultSet.getString("c_name_uk"),
                                resultSet.getTimestamp("c_created_at").toLocalDateTime(),
                                resultSet.getTimestamp("c_updated_at").toLocalDateTime()
                        )
                );
    }

    @Override
    public Optional<CurrencyEntity> findFirstEntityByCurrencyAlphabeticCode(String currencyAlphabeticCode) {
        return this.namedParameterJdbcTemplate
                .query(
                        """
                                SELECT c.decimal_place AS c_decimal_place,
                                    c.name AS c_name,
                                    c.name_uk AS c_name_uk,
                                    c.created_at AS c_created_at,
                                    c.updated_at AS c_updated_at
                                FROM currencies c
                                WHERE c.alphabetic_code = :currencyAlphabeticCode
                                LIMIT 1;
                                """,
                        new MapSqlParameterSource(){{
                            addValue("currencyAlphabeticCode", currencyAlphabeticCode, Types.CHAR);
                        }},
                        (ResultSet resultSet, int rowNum) -> new CurrencyEntity(
                                currencyAlphabeticCode,
                                resultSet.getInt("c_decimal_place"),
                                resultSet.getString("c_name"),
                                resultSet.getString("c_name_uk"),
                                resultSet.getTimestamp("c_created_at").toLocalDateTime(),
                                resultSet.getTimestamp("c_updated_at").toLocalDateTime()
                        )
                )
                .stream()
                .findFirst();
    }
}
