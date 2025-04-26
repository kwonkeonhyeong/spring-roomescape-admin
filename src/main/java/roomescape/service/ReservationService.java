package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;
import roomescape.reservation.Reservation;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeService reservationTimeService;

    public ReservationService(ReservationDao reservationDao, ReservationTimeService reservationTimeService) {
        this.reservationDao = reservationDao;
        this.reservationTimeService = reservationTimeService;
    }

    // todo 예약 시간과 관련된 검증 책임 (예약하고자 하는 시간이 현재 시간보다 과거의 시간인지) Service에게 위임
    public List<ReservationResponse> findAll() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(reservation -> {
                            ReservationTimeResponse reservationTimeResponse = new ReservationTimeResponse(reservation.getTime().getId(), reservation.getTime().getStartAt());
                            return new ReservationResponse(reservation.getId(),reservation.getName(), reservation.getDate(), reservationTimeResponse);
                }).toList();
    }

    public ReservationResponse insert(ReservationRequest request) {
        Long reservationId = reservationDao.insert(request);
        Long timeId = request.timeId();
        ReservationTimeResponse reservationTimeResponse = reservationTimeService.findById(timeId);
        return new ReservationResponse(
                reservationId, request.name(), request.date(),
                reservationTimeResponse
        );
    }

    public void delete(Long id) {
        reservationDao.delete(id);
    }
}
