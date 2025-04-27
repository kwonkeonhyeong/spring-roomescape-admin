package roomescape.reservation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class ReservationTimeTest {

    @Test
    @DisplayName("ReservationTime 생성 테스트")
    void generateReservationTime() {
        //given
        Long timeId = 1L;
        LocalTime time = LocalTime.of(10, 31);

        //when & then
        assertThatCode(() -> new ReservationTime(timeId, time)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("예약 시간 null 테스트")
    void throwExceptionIfReservationTime() {
        //given
        Long timeId = 1L;
        LocalTime time = null;

        //when & then
        assertThatThrownBy(() -> new ReservationTime(timeId, time)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("generateUnAvailableDateTime")
    @DisplayName("예약 시간이 현재 시간보다 과거인 경우 False")
    void returnFalseIfReservationDateTimeIsInThePast(LocalDate date, LocalTime time) {
        //given
        Long timeId = 1L;
        ReservationTime reservationTime = new ReservationTime(timeId, time);

        // then
        boolean isPast = reservationTime.isPast(date);

        //when & then
        assertThat(isPast).isTrue();
    }

    static Stream<Arguments> generateUnAvailableDateTime() {
        return Stream.of(
                Arguments.arguments(
                        LocalDate.now(),
                        LocalTime.now().minusHours(1)
                ),
                Arguments.arguments(
                        LocalDate.now(),
                        LocalTime.now()
                ),
                Arguments.arguments(
                        LocalDate.now(),
                        LocalTime.now().minusMinutes(1)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("generateAvailableDateTime")
    @DisplayName("예약 시간이 현재 시간보다 미래인 경우 True")
    void returnTrueIfReservationDateTimeIsInTheFuture(LocalDate date, LocalTime time) {
        //given
        Long timeId = 1L;
        ReservationTime reservationTime = new ReservationTime(timeId, time);

        // then
        boolean isPast = reservationTime.isPast(date);

        //when & then
        assertThat(isPast).isFalse();
    }

    static Stream<Arguments> generateAvailableDateTime() {
        return Stream.of(
                Arguments.arguments(
                        LocalDate.now(),
                        LocalTime.now().plusMinutes(1)
                ),
                Arguments.arguments(
                        LocalDate.now(),
                        LocalTime.now().plusHours(1)
                )
        );
    }
}
