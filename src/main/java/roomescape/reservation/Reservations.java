package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class Reservations {

    private final ReservationDao reservationDao;

    public Reservations(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Long add(ReservationRequest reservationRequest) {
        return reservationDao.insert(reservationRequest);
    }

    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public void deleteById(Long id) {
        reservationDao.delete(id);
    }
}
