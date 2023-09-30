package dat3.car.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dat3.car.entity.Reservation;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class ReservationResponse {
    int id;
    int carId;
    String brand;
    String model;
    double pricePrDay;
    //@JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    LocalDate reservationDate;

    public ReservationResponse(Reservation reservation){
        this.id = reservation.getId();
        this.reservationDate = reservation.getRentalDate();
        this.carId = reservation.getCar().getId();
        this.brand = reservation.getCar().getBrand();
        this.model = reservation.getCar().getModel();
        this.pricePrDay = reservation.getCar().getPricePrDay();
    }
}
