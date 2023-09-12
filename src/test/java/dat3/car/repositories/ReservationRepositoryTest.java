package dat3.car.repositories;

import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ReservationRepositoryTest {
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    MemberRepository memberRepository;

    boolean isInitialized = false;

    int res1Id, res2Id;

    @BeforeEach
    public void setUp() {
        if (!isInitialized) {
            reservationRepository.deleteAll();
            Car car1 = new Car("BMW", "M5", 150.0, 10);
            Car car2 = new Car("Mercedes", "E250", 100.0, 20);
            Member member1 = new Member("user1", "pass1", "John", "Doe", "email1", "address1", "city1", "zip1");
            Member member2 = new Member("user2", "pass2", "Jane", "Smith", "email2", "address2", "city2", "zip2");
            carRepository.save(car1);
            carRepository.save(car2);
            memberRepository.save(member1);
            memberRepository.save(member2);

            Reservation res1 = reservationRepository.save(new Reservation(LocalDate.now(), car1, member1));
            Reservation res2 = reservationRepository.save(new Reservation(LocalDate.now().plusDays(2), car2, member2));
            res1Id = res1.getId();
            res2Id = res2.getId();
            isInitialized = true;
        }
    }


    @Test
    public void testAll() {
        assertEquals(2, reservationRepository.count());
    }

    @Test
    public void testFindById() {
        Reservation reservation = reservationRepository.findById(res1Id).get();
        assertEquals("BMW", reservation.getCar().getBrand());
    }

    @Test
    public void testDeleteAll() {
        reservationRepository.deleteAll();
        assertEquals(0, reservationRepository.count());
    }

    @Test
    public void testGetReservationsByMember_Username() {
        assertEquals(1, reservationRepository.getReservationsByMember_Username("user1").size());
    }
}
