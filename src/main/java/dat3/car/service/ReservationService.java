package dat3.car.service;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.entity.Car;
import dat3.car.entity.Reservation;
import dat3.car.repositories.CarRepository;
import dat3.car.repositories.MemberRepository;
import dat3.car.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

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
        Reservation reservation = new Reservation();
        reservation.setRentalDate(body.getRentalDate());
        reservation.setCar(carRepository.findById(body.getCarId()).get());
        reservation.setMember(memberRepository.findById(body.getUsername()).get());
        reservation = reservationRepository.save(reservation);
        return new ReservationResponse(reservation);

    }
}
