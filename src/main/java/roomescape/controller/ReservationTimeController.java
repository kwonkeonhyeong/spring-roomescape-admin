package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.reservation.ReservationTime;

import java.util.List;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ResponseEntity<ReservationTime> save(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        Long id = reservationTimeDao.save(reservationTimeRequest);
        return ResponseEntity.ok(reservationTimeRequest.toEntity(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationTime>> searchReservations() {
        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
