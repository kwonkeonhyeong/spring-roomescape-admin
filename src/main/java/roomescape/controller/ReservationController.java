package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservation;

import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
        List<Reservation> findReservations = reservationDao.findAll();
        return ResponseEntity.ok().body(findReservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Long id = reservationDao.insert(reservationRequest);
        return ResponseEntity.ok().body(reservationRequest.toEntity(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
