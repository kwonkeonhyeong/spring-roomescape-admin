package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        validate(id, startAt);
        this.id = id;
        this.startAt = startAt;
    }

    private void validate(Long id, LocalTime startAt) {
        try {
            Objects.requireNonNull(id);
            Objects.requireNonNull(startAt);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("올바른 요청이 아닙니다");
        }
    }

    public boolean isBefore(LocalDate date) {
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
