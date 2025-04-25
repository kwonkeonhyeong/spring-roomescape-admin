package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationRequest;
import roomescape.reservation.Reservation;
import roomescape.reservation.ReservationTime;

import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    public ReservationController(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> searchReservations() {
        List<Reservation> findReservations = reservationDao.findAll();
        return ResponseEntity.ok().body(findReservations);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@RequestBody ReservationRequest reservationRequest) {
        Long id = reservationDao.insert(reservationRequest);
        Long timeId = reservationRequest.getTimeId();
        ReservationTime reservationTime = reservationTimeDao.findById(timeId);
        return ResponseEntity.ok().body(reservationRequest.toEntity(id, reservationTime));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationDao.delete(id);
        return ResponseEntity.ok().build();
    }
}
