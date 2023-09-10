package dat3.car.repositories;

import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ReservationRepository reservationRepository;

    boolean isInitialized = false;
    int car1Id, car2Id;

    @BeforeEach
    public void setUp() {
        if (!isInitialized) {
            carRepository.deleteAll();
            Car car1 = carRepository.save(new Car("BMW", "M5", 150.0, 10));
            car1Id = car1.getId();
            Car car2 = carRepository.save(new Car("Mercedes", "E250", 100.0, 20));
            car2Id = car2.getId();
            isInitialized = true;
        }
    }


    @Test
    public void testAll() {
        assertEquals(2, carRepository.count());
    }

    @Test
    public void deleteAll() {
        carRepository.deleteAll();
        assertEquals(0, carRepository.count());
    }

    @Test
    public void testFindById() {
        Car car = carRepository.findById(car1Id).get();
        assertEquals("BMW", car.getBrand());
    }

    @Test
    public void testAddCar() {
        carRepository.save(new Car("Audi", "A4", 200.0, 30));
        int carId = carRepository.findAll().get(2).getId();
        assertEquals(3, carRepository.count());
        assertEquals("Audi", carRepository.findById(carId).get().getBrand());
    }

    @Test
    public void testFindByBrandAndModel() {
        assertEquals(1, carRepository.getByBrandAndModel("BMW", "M5").size());
        assertEquals(1, carRepository.getByBrandAndModel("Mercedes", "E250").size());
        assertEquals(0, carRepository.getByBrandAndModel("Audi", "A4").size());
        assertEquals("BMW", carRepository.getByBrandAndModel("BMW", "M5").get(0).getBrand());
    }

    @Test
    public void testGetByReservationsEmpty() {
        Car car3 = new Car("Audi", "A4", 200.0, 30);
        Member member = new Member("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        memberRepository.save(member);
        reservationRepository.save(new Reservation(LocalDate.now(), car3, member));


        carRepository.save(car3);
        carRepository.flush();
        assertEquals(3, carRepository.findAll().size());
        assertEquals(2, carRepository.getByReservationsEmpty().size());

    }

    @Test
    public void testGetCarsByBestDiscount(){
        Car car3 = carRepository.save(new Car("Audi", "A4", 200.0, 30));
        Car car4 = carRepository.save(new Car("Toyota", "Prius", 100.0, 5));

        List<Car> cars = carRepository.getCarsByBestDiscountNotNullOrderByBestDiscountDesc();

        assertEquals(4, cars.size());
        assertEquals(30, cars.get(0).getBestDiscount());
        assertEquals("Audi", cars.get(0).getBrand());
        assertEquals("Mercedes", cars.get(1).getBrand());
        assertEquals("BMW", cars.get(2).getBrand());
        assertEquals("Toyota", cars.get(3).getBrand());
    }


}