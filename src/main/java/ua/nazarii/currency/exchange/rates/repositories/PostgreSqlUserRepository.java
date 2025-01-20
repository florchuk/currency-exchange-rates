package ua.nazarii.currency.exchange.rates.repositories;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import ua.nazarii.currency.exchange.rates.entities.UserEntity;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PostgreSqlUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PostgreSqlUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return this.namedParameterJdbcTemplate
                .query(
                        """
                                SELECT u.id AS u_id,
                                    u.password AS u_password,
                                    u.is_account_non_expired AS u_is_account_non_expired,
                                    u.is_account_non_locked AS u_is_account_non_locked,
                                    u.is_credentials_non_expired AS u_is_credentials_non_expired,
                                    u.is_enabled AS u_is_enabled,
                                    u.created_at AS u_created_at,
                                    u.updated_at AS u_updated_at,
                                    a.name AS a_name
                                FROM users u
                                LEFT JOIN user_authorities u_a ON (
                                    u.id = u_a.user_id
                                )
                                LEFT JOIN authorities a ON (
                                    u_a.authority_id = a.id
                                )
                                WHERE u.username = :username;
                                """,
                        new MapSqlParameterSource() {{
                            addValue("username", username, Types.VARCHAR);
                        }},
                        (ResultSet resultSet) -> {
                            if (resultSet.next()) {
                                UserEntity userEntity = new UserEntity(
                                        resultSet.getInt("u_id"),
                                        username,
                                        resultSet.getString("u_password"),
                                        null,
                                        resultSet.getBoolean("u_is_account_non_expired"),
                                        resultSet.getBoolean("u_is_account_non_locked"),
                                        resultSet.getBoolean("u_is_credentials_non_expired"),
                                        resultSet.getBoolean("u_is_enabled"),
                                        resultSet.getTimestamp("u_created_at").toLocalDateTime(),
                                        resultSet.getTimestamp("u_updated_at").toLocalDateTime()
                                );
                                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                                String authorityName = resultSet.getString("a_name");

                                if (authorityName != null) {
                                    authorities.add(new SimpleGrantedAuthority(authorityName));
                                }

                                while (resultSet.next() && resultSet.getString("a_name") != null) {
                                    authorities.add(
                                            new SimpleGrantedAuthority(resultSet.getString("a_name"))
                                    );
                                }

                                userEntity.setAuthorities(authorities);

                                return Optional.of(userEntity);
                            } else {
                                return Optional.empty();
                            }
                        }
                );
    }
}
