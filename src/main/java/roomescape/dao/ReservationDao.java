package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservation;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long insert(ReservationRequest reservationRequest) {
        String sql = "INSERT INTO reservation(name, date, time) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[]{"id"});
                    ps.setString(1, reservationRequest.getName());
                    ps.setString(2, reservationRequest.getDate().toString());
                    ps.setString(3, reservationRequest.getTime().toString());
                    return ps;
                }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Reservation findById(Long id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rn) -> {
                    return new Reservation(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime()
                    );
                }, id);
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, name, date, time FROM reservation";
        return jdbcTemplate.query(
                sql,
                (rs, rn) -> {
                    Reservation reservation = new Reservation(
                            rs.getLong("id"),
                            rs.getString("id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime()
                    );
                    return reservation;
                });
    }

    public int delete(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        return jdbcTemplate.update(sql, Long.valueOf(id));
    }
}
