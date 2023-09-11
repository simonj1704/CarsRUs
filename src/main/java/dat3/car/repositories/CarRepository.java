package dat3.car.repositories;

import dat3.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> getByBrandAndModel(String brand, String model);

    List<Car> getByReservationsEmpty();

    List<Car> getCarsByBestDiscountNotNullOrderByBestDiscountDesc();

    //TODO setup test and service to use this method
    @Query("SELECT AVG(c.pricePrDay) FROM Car c")
    double getAveragePricePrDay();
}
