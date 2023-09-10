package dat3.car.service;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Car;
import dat3.car.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceMockitoTest {

    //@InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carService = new CarService(carRepository);
    }

    private Car makeCar(int id, String brand, String model, double pricePrDay, int bestDiscount) {
        Car car = new Car(id, brand, model, pricePrDay, bestDiscount);
        car.setCreated(LocalDateTime.now());
        car.setEdited(LocalDateTime.now());
        return car;
    }


    private Car makeCar2() {
        Car car = new Car(1, "BMW", "M5", 150.5, 10);
        car.setCreated(LocalDateTime.now());
        car.setEdited(LocalDateTime.now());
        return car;
    }

    @Test
    public void testGetCars() {
        // Define a mock behavior
        Car c1 = makeCar(1, "BMW", "M5", 150.5, 10);
        Car c2 = makeCar(2, "Mercedes", "E250", 100.0, 20);
        when(carRepository.findAll()).thenReturn(List.of(c1, c2));
        List<CarResponse> responses = carService.getCars(true);
        // Assertions
        assertEquals(2, responses.size(), "Expected 2 cars");
        assertNotNull(responses.get(0).getCreated(), "Dates must be set since true was passed to getCars");
    }

    @Test
    public void testGetCarsNoDetails() {
        // Define a mock behavior
        Car c1 = makeCar(1, "BMW", "M5", 150.5, 10);
        Car c2 = makeCar(2, "Mercedes", "E250", 100.0, 20);
        when(carRepository.findAll()).thenReturn(List.of(c1, c2));
        List<CarResponse> responses = carService.getCars(false);
        // Assertions
        assertEquals(2, responses.size(), "Expected 2 cars");
        assertNull(responses.get(0).getCreated(), "Dates must be null since true was passed to getCars");
    }


    @Test
    public void testFindById() {
        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(makeCar2()));
        CarResponse response = carService.findById(1, true);
        assertEquals(1, response.getId());
        assertNotNull(response.getCreated(), "Dates must be set since true was passed to getCars");
    }

    @Test
    public void testFindById_NotFount() {
        when(carRepository.findById(999)).thenReturn(java.util.Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> {
                    carService.findById(999, true);
                });
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    public void testFindCarByBrandAndModel() {
        Car c1 = makeCar(1, "BMW", "M5", 150.5, 10);
        Car c2 = makeCar(2, "BMW", "M5", 100.0, 20);
        when(carRepository.getByBrandAndModel("BMW", "M5")).thenReturn(List.of(c1, c2));
        List<CarResponse> responses = carService.findCarByBrandAndModel("BMW", "M5");
        assertEquals(2, responses.size(), "Expected 2 cars");
        assertEquals("BMW", responses.get(0).getBrand());
        assertEquals("M5", responses.get(0).getModel());
    }

    @Test
    public void testAddCar() {
        Car newCar = makeCar(1, "BMW", "M5", 150.5, 10);
        when(carRepository.save(any(Car.class))).thenReturn(newCar);
        CarRequest cr = new CarRequest(newCar);
        CarResponse response = carService.addCar(cr);
        assertEquals(1, response.getId());
        assertEquals("BMW", response.getBrand());
    }

    @Test
    public void testEditCarWithExistingId() {
        Car car = makeCar(1, "BMW", "M5", 150.5, 10);
        Car changedCar = makeCar(1, "Mercedes", "M5", 100, 10);
        CarRequest cr = new CarRequest(changedCar);
        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));


        carService.editCar(cr, 1);

        assertEquals(1, car.getId());
        assertEquals("Mercedes", car.getBrand());
        assertEquals("M5", car.getModel());
        assertEquals(100, car.getPricePrDay());
        assertEquals(10, car.getBestDiscount());

    }

    @Test
    public void testEditCarWithNON_ExistingId() {
        Car car = new Car();

        when(carRepository.findById(999)).thenReturn(java.util.Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> {
                    carService.editCar(new CarRequest(car), 999);
                });
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    public void testEditCarChangePrimaryKey() {
        Car car = makeCar2();
        CarRequest cr = new CarRequest(car);
        cr.setId(3);

        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> {
                    carService.editCar(cr, 1);
                });
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    public void testSetBestDiscountOnCar() {
        Car car = makeCar2();
        when(carRepository.findById(1)).thenReturn(java.util.Optional.of(car));
        carService.setBestDiscountOnCar(1, 20);
        assertEquals(20, car.getBestDiscount());
    }

    @Test
    public void testDeleteCarById(){
        int testId = 1;
        Car car = makeCar2();
        when(carRepository.findById(testId)).thenReturn(java.util.Optional.of(car));
        carService.deleteCarById(testId);
        assertEquals(0, carRepository.count());
        verify(carRepository).delete(car);
    }

    @Test
    public void testDeleteCarById_CarNotFound(){
        int testId = 1;
        when(carRepository.findById(testId)).thenReturn(java.util.Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> {
                    carService.deleteCarById(testId);
                });
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

}
