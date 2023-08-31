package dat3.car.repositories;

import dat3.car.entity.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;
    boolean isInitialized = false;
    int car1Id, car2Id;

    @BeforeEach
    public void setUp(){
        if(!isInitialized){
            carRepository.deleteAll();
            Car car1 = carRepository.save(new Car("BMW", "M5", 150.5, 10));
            car1Id = car1.getId();
            Car car2 = carRepository.save(new Car( "Mercedes", "E250", 100.0, 20));
            car2Id = car2.getId();
            isInitialized = true;
        }
    }


    @Test
    public void testAll(){
        assertEquals(2, carRepository.count());
    }

    @Test
    public void deleteAll(){
        carRepository.deleteAll();
        assertEquals(0, carRepository.count());
    }

    @Test
    public void testFindById(){
        Car car = carRepository.findById(car1Id).get();
        assertEquals("BMW", car.getBrand());
    }

    @Test
    public void testAddCar(){
        carRepository.save(new Car("Audi", "A4", 200.0, 30));
        int carId = carRepository.findAll().get(2).getId();
        assertEquals(3, carRepository.count());
        assertEquals("Audi", carRepository.findById(carId).get().getBrand());
    }


}