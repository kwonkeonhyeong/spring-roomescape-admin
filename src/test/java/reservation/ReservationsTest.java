package reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservations;
import roomescape.reservation.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationsTest {

    private static final TestDataSource TEST_DATA_SOURCE = new TestDataSource();
    private final Reservations reservations = new Reservations(new ReservationDao(new JdbcTemplate(TEST_DATA_SOURCE)));

    @BeforeAll
    static void beforeAll() {
        String query = "CREATE TABLE reservation ( id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, date VARCHAR(255) NOT NULL, time VARCHAR(255) NOT NULL, PRIMARY KEY (id))";
        try (
                Connection connection = TEST_DATA_SOURCE.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)
        ) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("예약 목록 확인")
    @Test
    void checkReservations() {
        List<ReservationRequest> reservationRequests = List.of(
                new ReservationRequest("test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13)),
                new ReservationRequest("test2", LocalDate.of(2025, 4, 20), LocalTime.of(13, 13)),
                new ReservationRequest("test3", LocalDate.of(2025, 4, 21), LocalTime.of(14, 13))
        );
        for (ReservationRequest reservationRequest : reservationRequests) {
            reservations.add(reservationRequest);
        }

        List<Reservation> findReservations = reservations.findAll();

        Assertions.assertThat(reservationRequests.stream().map(reservationRequest -> reservationRequest.toEntity(1L))).isEqualTo(findReservations);
    }

    @DisplayName("예약 추가 확인")
    @Test
    void addReservationTest() {
        ReservationRequest reservationRequest = new ReservationRequest("test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        Long id = reservations.add(reservationRequest);
        Reservation storedReservation = reservations.findById(id);

        Assertions.assertThat(storedReservation).isEqualTo(reservationRequest.toEntity(id));
    }

    @DisplayName("예약 삭제 확인")
    @Test
    void deleteReservationTest() {
        ReservationRequest reservationRequest = new ReservationRequest("test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        Long id = reservations.add(reservationRequest);
        List<Reservation> findReservation = reservations.findAll();
        reservations.deleteById(id);
        Assertions.assertThat(findReservation).isEmpty();
    }
}
