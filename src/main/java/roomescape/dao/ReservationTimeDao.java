package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationTimeRequest;
import roomescape.reservation.ReservationTime;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ReservationTimeRequest reservationTimeRequest) {
        String sql = "INSERT INTO reservation_time( start_at ) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
                    ps.setString(1,reservationTimeRequest.startAt().toString());
                    return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";

        return jdbcTemplate.query(sql,  (rs, rn) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime()
            );
        });
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,  (rs, rn) -> {
            return new ReservationTime(
                    rs.getLong("id"),
                    rs.getTime("start_at").toLocalTime()
            );
        },id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
