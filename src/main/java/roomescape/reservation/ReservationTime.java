package roomescape.reservation;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private static final String VALIDATION_MESSAGE = "올바른 요청이 아닙니다";

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validateStartAt(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validateStartAt(LocalTime startAt) {
        if (startAt == null) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    public boolean isBefore(LocalTime time) {
        return startAt.isBefore(time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
