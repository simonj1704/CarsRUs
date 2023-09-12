package dat3.car.repositories;

import dat3.car.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> getReservationsByMember_Username(String memberId);
}
