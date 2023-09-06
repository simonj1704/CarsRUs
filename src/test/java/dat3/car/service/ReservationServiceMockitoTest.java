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

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService(reservationRepository, carRepository, memberRepository);
    }

    private Reservation makeReservation(LocalDate rentalDate, Car car, Member member) {
        Reservation reservation = new Reservation(rentalDate, car, member);
        reservation.setCreated(LocalDateTime.now());
        reservation.setEdited(LocalDateTime.now());
        return reservation;
    }

    private Reservation makeReservation2() {
        Car car = new Car(1, "BMW", "M5", 150.5, 10);
        Member member = new Member("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        memberRepository.save(member);
        carRepository.save(car);
        Reservation reservation = new Reservation( LocalDate.now(), car, member);
        reservation.setCreated(LocalDateTime.now());
        reservation.setEdited(LocalDateTime.now());
        return reservation;
    }


    @Test
    public void testReserveCar_Success(){
        Reservation r1 = makeReservation2();
        ReservationRequest reservationRequest = new ReservationRequest(r1);

        // Define a mock behavior
        when(reservationRepository.save(any())).thenReturn(r1);
        when(memberRepository.findById(any())).thenReturn(Optional.of(r1.getMember()));
        when(carRepository.findById(any())).thenReturn(Optional.of(r1.getCar()));
        ReservationResponse response = reservationService.reserveCar(reservationRequest);
        //Assertions
        assertEquals(r1.getId(), response.getId());
        assertEquals(r1.getRentalDate(), response.getReservationDate());
        assertEquals(r1.getCar().getId(), response.getCarId());
    }

    @Test
    public void testReserveCar_DateInPast(){
        ReservationRequest reservationRequest = new ReservationRequest(makeReservation2());
        reservationRequest.setRentalDate(LocalDate.of(2020, 1, 1));
        //Assertions
        assertThrows(ResponseStatusException.class, () -> {
            reservationService.reserveCar(reservationRequest);
        }
        );
    }

    @Test
    public void testReserveCar_CarAlreadyReserved(){
        Car car = new Car();

        Reservation r1 = makeReservation(LocalDate.now(), car, new Member());
        Reservation r2 = makeReservation(LocalDate.now(), car, new Member());

        // Define a mock behavior
        when(memberRepository.findById(any())).thenReturn(Optional.of(r1.getMember()));
        when(carRepository.findById(any())).thenReturn(Optional.of(r1.getCar()));



        //Assertions
        assertThrows(ResponseStatusException.class, () -> {
                    reservationService.reserveCar(new ReservationRequest(r1));
                }
        );
    }

}
