package dat3.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dat3.car.entity.Reservation;
import dat3.car.repositories.CarRepository;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class ReservationRequest {
    //@JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate rentalDate;
    int carId;
    String username;


    public ReservationRequest(Reservation reservation){
        this.rentalDate = reservation.getRentalDate();
        this.carId = reservation.getCar().getId();
        this.username = reservation.getMember().getUsername();
    }
}
