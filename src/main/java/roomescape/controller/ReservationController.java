package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.service.ReservationService;

import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> searchReservations() {
        List<ReservationResponse> findReservations = reservationService.findAll();
        return ResponseEntity.ok().body(findReservations);
    }

    @PostMapping
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest) {
        try{
            ReservationResponse reservationResponse = reservationService.insert(reservationRequest);
            return ResponseEntity.ok().body(reservationResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
