package dat3.car.service;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repositories.CarRepository;
import dat3.car.repositories.MemberRepository;
import dat3.car.repositories.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    CarRepository carRepository;
    MemberRepository memberRepository;

    public ReservationService(ReservationRepository reservationRepository, CarRepository carRepository, MemberRepository memberRepository){
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
    }

    public ReservationResponse reserveCar(ReservationRequest body){
        if (body.getRentalDate().isBefore(LocalDate.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Date in past not allowed");
        }
        Member member = memberRepository.findById(body.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No member with this id found"));
        Car car = carRepository.findById(body.getCarId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No car with this id found"));

        for (Reservation reservation : carRepository.findById(body.getCarId()).get().getReservations()) {
            if(reservation.getRentalDate().equals(body.getRentalDate())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car already reserved on this date");
            }
        }

        Reservation res = reservationRepository.save(new Reservation(body.getRentalDate(), car, member));
        return new ReservationResponse(res);

    }
}
