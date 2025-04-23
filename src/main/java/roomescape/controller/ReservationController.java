package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservation;
import roomescape.reservation.Reservations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final Reservations reservations;

    public ReservationController(Reservations reservations) {
        this.reservations = reservations;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
        List<Reservation> findReservations = reservations.findAll();
        return ResponseEntity.ok().body(findReservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Long id = reservations.add(reservationRequest);
        return ResponseEntity.ok().body(reservationRequest.toEntity(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservations.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
