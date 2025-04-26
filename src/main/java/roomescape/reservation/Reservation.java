package roomescape.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation {
    private static final String VALIDATION_MESSAGE = "올바른 요청이 아닙니다";

    private final Long id;
    private final String name;
    private final LocalDate date;
    private final ReservationTime time;

    public Reservation(Long id, String name, LocalDate date, ReservationTime time) {
        validate(name, date, time);
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    private void validate(String name, LocalDate date, ReservationTime time) {
        try {
            validateName(name);
            Objects.requireNonNull(date);
            Objects.requireNonNull(time);
            validateReservationDateTime(date,time);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    private void validateReservationDateTime(LocalDate date, ReservationTime time) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate nowDate = now.toLocalDate();
        if(date.isBefore(nowDate)) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
        if(time.isBefore(date)) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
