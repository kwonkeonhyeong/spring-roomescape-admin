package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.reservation.ReservationTime;
import roomescape.service.ReservationTimeService;

import java.util.List;

@RequestMapping("/times")
@RestController
public class ReservationTimeController {
    private final ReservationTimeService reservationTimeService;

    public ReservationTimeController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @PostMapping
    public ResponseEntity<ReservationTimeResponse> save(@RequestBody ReservationTimeRequest reservationTimeRequest) {
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.save(reservationTimeRequest);
        return ResponseEntity.ok(reservationTimeResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationTimeResponse>> searchReservations() {
        List<ReservationTimeResponse> reservationTimes = reservationTimeService.findAll();
        return ResponseEntity.ok(reservationTimes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable Long id) {
        reservationTimeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
