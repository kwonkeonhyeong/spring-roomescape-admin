package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.dto.ReservationTimeRequest;
import roomescape.reservation.ReservationTime;

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
        return ResponseEntity.ok().body(reservationTimeRequest.toEntity(id));
    }
}
