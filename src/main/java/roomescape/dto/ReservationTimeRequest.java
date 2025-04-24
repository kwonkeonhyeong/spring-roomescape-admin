package roomescape.dto;

import roomescape.reservation.ReservationTime;

import java.time.LocalTime;

public class ReservationTimeRequest {
    private final LocalTime startAt;

    public ReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
