package dat3.car.service;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repositories.CarRepository;
import dat3.car.repositories.MemberRepository;
import dat3.car.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceMockitoTest {

    //@InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private MemberRepository memberRepository;

    Car car1, car2;
    Member member1, member2;
    LocalDate date1, date2;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, carRepository, memberRepository);
        car1 = new Car(1, "Audi", "A4", 100.0, 10);
        car1.setReservations(new ArrayList<>());
        car2 = new Car(2, "BMW", "M3", 200.0, 20);
        car2.setReservations(new ArrayList<>());
        member1 = new Member("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        member2 = new Member("user2", "pw2", "fn2", "ln2", "email2", "street2", "city2", "zip2");
        date1 = LocalDate.now().plusDays(1);
        date2 = LocalDate.now().plusDays(2);

    }



    @Test
    public void testReserveCar_Success(){
        Reservation reservation = new Reservation();
        reservation.setRentalDate(date1);
        reservation.setCar(car1);
        reservation.setMember(member1);
        reservation.setId(1);

        ReservationRequest reservationRequest = new ReservationRequest(reservation);

        when(memberRepository.findById(any())).thenReturn(Optional.of(member1));
        when(carRepository.findById(any())).thenReturn(Optional.of(car1));
        when(reservationRepository.save(any())).thenReturn(reservation);



        ReservationResponse reservationResponse = reservationService.reserveCar(reservationRequest);

        assertEquals(1, reservationResponse.getId());
        assertEquals(date1, reservationResponse.getReservationDate());
        assertEquals(car1.getId(), reservationResponse.getCarId());
    }

    @Test
    public void testReserveCar_DateInPast(){
        ReservationRequest reservationRequest = new ReservationRequest(new Reservation(date1, car1, member1));
        reservationRequest.setRentalDate(LocalDate.of(2020, 1, 1));
        //Assertions
        assertThrows(ResponseStatusException.class, () -> {
            reservationService.reserveCar(reservationRequest);
        }
        );
    }

    @Test
    public void testReserveCar_CarAlreadyReserved(){

    }

}
