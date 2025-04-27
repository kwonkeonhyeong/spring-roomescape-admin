package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private static final String VALIDATION_MESSAGE = "올바른 요청이 아닙니다";

    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validate(startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(LocalTime startAt) {
        try {
            Objects.requireNonNull(startAt);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    public boolean isPast(LocalDate date) {
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        return date.equals(nowDate) && startAt.isBefore(nowTime);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
