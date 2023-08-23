package dat3.car.repositories;

import dat3.car.entity.Car;
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

    @BeforeEach
    public void setUp(){
        if(!isInitialized){
            carRepository.deleteAll();
            carRepository.save(new Car(1, "BMW", "M5", 150.5, 10));
            carRepository.save(new Car(2, "Mercedes", "E250", 100.0, 20));
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
}