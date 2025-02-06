package ua.nazarii.currency.exchange.rates.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.nazarii.currency.exchange.rates.entities.ArchiveRateEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Optional;

@Repository
public class PostgreSqlArchiveRateRepository implements ArchiveRateRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgreSqlArchiveRateRepository(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<ArchiveRateEntity> findFirstArchiveRateEntityByRateIdOrderByCreatedAtDesc(Integer rateId) {
        return this.namedParameterJdbcTemplate
                .query(
                        """
                                SELECT ar.id AS ar_id,
                                    ar.unit AS ar_unit,
                                    ar.buy_rate AS ar_buy_rate,
                                    ar.sale_rate AS ar_sale_rate,
                                    ar.created_at AS ar_created_at,
                                    ar.updated_at AS ar_updated_at
                                FROM archive_rates ar
                                WHERE ar.rate_id = :rateId
                                ORDER BY ar.created_at DESC
                                LIMIT 1;
                                """,
                        new MapSqlParameterSource(){{
                            addValue("rateId", rateId, Types.INTEGER);
                        }},
                        (ResultSet resultSet, int rowNum) -> new ArchiveRateEntity(
                                resultSet.getLong("ar_id"),
                                rateId,
                                resultSet.getInt("ar_unit"),
                                resultSet.getDouble("ar_buy_rate"),
                                resultSet.getDouble("ar_sale_rate"),
                                resultSet.getTimestamp("ar_created_at").toLocalDateTime(),
                                resultSet.getTimestamp("ar_updated_at").toLocalDateTime()
                        )
                )
                .stream()
                .findFirst();
    }

    @Override
    public int insertArchiveRateEntity(ArchiveRateEntity archiveRateEntity) {
        archiveRateEntity.setId(
                this.jdbcTemplate.queryForObject("SELECT NEXTVAL('archive_rates_sequence') AS id;", Long.class)
        );

        return this.namedParameterJdbcTemplate
                .update(
                        """
                                INSERT INTO archive_rates
                                    (
                                        id,
                                        rate_id,
                                        unit,
                                        buy_rate,
                                        sale_rate,
                                        created_at,
                                        updated_at
                                    )
                                VALUES
                                    (
                                        :id,
                                        :rateId,
                                        :unit,
                                        :buyRate,
                                        :saleRate,
                                        :createdAt,
                                        :updatedAt
                                    );
                                """,
                        new MapSqlParameterSource(){{
                            addValue("id", archiveRateEntity.getId(), Types.BIGINT);
                            addValue("rateId", archiveRateEntity.getRateId(), Types.INTEGER);
                            addValue("unit", archiveRateEntity.getUnit(), Types.INTEGER);
                            addValue("buyRate", archiveRateEntity.getBuyRate(), Types.DOUBLE);
                            addValue("saleRate", archiveRateEntity.getSaleRate(), Types.DOUBLE);
                            addValue("createdAt", archiveRateEntity.getCreatedAt(), Types.TIMESTAMP);
                            addValue("updatedAt", archiveRateEntity.getUpdatedAt(), Types.TIMESTAMP);
                        }}

                );
    }

    @Override
    public int updateArchiveRateEntity(ArchiveRateEntity archiveRateEntity) {
        return this.namedParameterJdbcTemplate
                .update(
                        """
                                UPDATE archive_rates SET
                                    rate_id = :rateId,
                                    unit = :unit,
                                    buy_rate = :buyRate,
                                    sale_rate = :saleRate,
                                    created_at = :createdAt,
                                    updated_at = :updatedAt
                                WHERE id = :id;
                                """,
                        new MapSqlParameterSource(){{
                            addValue("id", archiveRateEntity.getId(), Types.BIGINT);
                            addValue("rateId", archiveRateEntity.getRateId(), Types.INTEGER);
                            addValue("unit", archiveRateEntity.getUnit(), Types.INTEGER);
                            addValue("buyRate", archiveRateEntity.getBuyRate(), Types.DOUBLE);
                            addValue("saleRate", archiveRateEntity.getSaleRate(), Types.DOUBLE);
                            addValue("createdAt", archiveRateEntity.getCreatedAt(), Types.TIMESTAMP);
                            addValue("updatedAt", archiveRateEntity.getUpdatedAt(), Types.TIMESTAMP);
                        }}
                );
    }
}
