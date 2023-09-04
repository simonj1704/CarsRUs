package dat3.car.service;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.entity.Car;
import dat3.car.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CarServiceH2Test {

    @Autowired
    CarRepository carRepository;
    CarService carService;

    Car c1, c2;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        c1 = carRepository.save(new Car( 1, "BMW", "M5", 150.5, 10));
        c2 = carRepository.save(new Car(2, "Mercedes", "E250", 100.0, 20));
        carService = new CarService(carRepository);
    }

    @Test
    void testGetCarsAllDetails() {
        List<CarResponse> responses = carService.getCars(true);
        assertEquals(2, responses.size(), "Expected 2 cars");
        assertEquals("BMW", responses.get(0).getBrand());
        assertNotNull(responses.get(0).getCreated(), "Dates must be set since true was passed to getCars");
    }

    @Test
    void testGetCarsNoDetails() {
        List<CarResponse> responses = carService.getCars(false);
        assertEquals(2, responses.size(), "Expected 2 cars");
        assertEquals("BMW", responses.get(0).getBrand());
        assertNull(responses.get(0).getCreated(), "Dates must be null since true was passed to getCars");
    }

    @Test
    void testFindByIdFound() {
        CarResponse response = carService.findById(c1.getId());
        assertEquals(c1.getId(), response.getId());
    }

    @Test
    void testFindByIdNotFound(){
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> carService.findById(999));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testAddCar(){
        CarRequest request = CarRequest.builder().
                brand("Audi").
                model("A4").
                pricePrDay(200.0).
                bestDiscount(30).
                build();
        CarResponse response = carService.addCar(request);


        assertEquals(3, carRepository.count());
        assertEquals("Audi", response.getBrand());
        assertNotNull(response.getId());
    }

    @Test
    void testEditCarWithExistingId(){
        CarRequest request = new CarRequest(c1);
        request.setModel("newModel");
        request.setPricePrDay(200.0);

        carService.editCar(request, c1.getId());

        carRepository.flush();
        CarResponse response = carService.findById(c1.getId());
        assertEquals("BMW", response.getBrand());
        assertEquals("newModel", response.getModel());
        assertEquals(200.0, response.getPricePrDay());
        assertEquals(10, response.getBestDiscount());

    }

    @Test
    void testEditCarWithNON_ExistingId(){
        CarRequest request = new CarRequest();

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> carService.editCar(request, 999));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testEditCarChangePrimaryKey(){
        CarRequest request = new CarRequest(c1);
        request.setId(999);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> carService.editCar(request, c1.getId()));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Cannot change ID", ex.getReason());
    }

    @Test
    void testSetBestDiscountOnCar(){
        carService.setBestDiscountOnCar(c1.getId(), 50);
        CarResponse response = carService.findById(c1.getId());
        assertEquals(50, response.getBestDiscount());
    }

    @Test
    void testDeleteCarById(){
        carService.deleteCarById(c1.getId());
        assertEquals(1, carRepository.count());
        assertFalse(carRepository.existsById(c1.getId()));
    }

    @Test
    void testDeleteCar_ThatDontExist(){
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> carService.deleteCarById(999));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}

