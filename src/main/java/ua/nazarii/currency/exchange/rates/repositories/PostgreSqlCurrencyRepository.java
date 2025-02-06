package ua.nazarii.currency.exchange.rates.repositories;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.nazarii.currency.exchange.rates.entities.CurrencyEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;

@Repository
public class PostgreSqlCurrencyRepository implements CurrencyRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgreSqlCurrencyRepository(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<CurrencyEntity> findCurrencyEntityByAlphabeticCode(String alphabeticCode) {
        return this.namedParameterJdbcTemplate
                .query(
                        """
                                SELECT c.decimal_place AS c_decimal_place,
                                    c.name AS c_name,
                                    c.name_uk AS c_name_uk,
                                    c.created_at AS c_created_at,
                                    c.updated_at AS c_updated_at
                                FROM currencies c
                                WHERE c.alphabetic_code = :alphabeticCode
                                LIMIT 1;
                                """,
                        new MapSqlParameterSource(){{
                            addValue("alphabeticCode", alphabeticCode, Types.CHAR);
                        }},
                        (ResultSet resultSet, int rowNum) -> new CurrencyEntity(
                                alphabeticCode,
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
