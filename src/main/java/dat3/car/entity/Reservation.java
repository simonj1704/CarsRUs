package dat3.car.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Lombok above
@Entity
public class Reservation extends AdminDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    LocalDate startDate;
    LocalDate endDate;
    double totalPrice;

    @ManyToOne
    Member member;

    @ManyToOne
    Car car;

    public Reservation(LocalDate startDate, LocalDate endDate, double totalPrice, Car car, Member member) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.member = member;
        this.car = car;
        car.addReservation(this);
        member.addReservation(this);

    }




}
