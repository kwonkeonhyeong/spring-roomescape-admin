package reservation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservations;
import roomescape.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationsTest {

    private Reservations reservations;

    @BeforeEach
    void beforeEach() {
        reservations = new Reservations();
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
        reservations.add(reservationRequest);
        List<Reservation> findReservation = reservations.findAll();
        assertAll(
                () -> Assertions.assertThat(findReservation.size()).isEqualTo(1),
                () -> Assertions.assertThat(findReservation.getFirst()).isEqualTo(reservationRequest.toEntity(1L))
        );
    }

    @DisplayName("예약 삭제 확인")
    @Test
    void deleteReservationTest() {
        ReservationRequest reservationRequest = new ReservationRequest("test1", LocalDate.of(2025, 4, 19), LocalTime.of(12, 13));
        reservations.add(reservationRequest);
        reservations.deleteById(1L);
        List<Reservation> findReservation = reservations.findAll();
        Assertions.assertThat(findReservation).isEmpty();
    }
}
