package dat3.car.service;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.entity.Car;
import dat3.car.repositories.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars(boolean includeAll) {
        List<Car> cars = carRepository.findAll();
        List<CarResponse> response = new ArrayList<>();
        for (Car car : cars){
            CarResponse cr = new CarResponse(car, includeAll);
            response.add(cr);
        }

        return response;
    }

    public CarResponse findById(int id) {
        Car car = getCarById(id);
        return new CarResponse(car, true);
    }

    public CarResponse addCar(CarRequest body) {
        if(carRepository.existsById(body.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"This car already exists");
        }
        Car newCar = carRepository.save(CarRequest.getCarRequest(body));

        return new CarResponse(newCar, true);
    }

    public ResponseEntity<Boolean> editCar(CarRequest body, int id) {
        Car car = getCarById(id);
        if(body.getId() != id){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cannot change ID");
        }
        car.setModel(body.getModel());
        car.setBrand(body.getBrand());
        car.setPricePrDay(body.getPricePrDay());
        car.setBestDiscount(body.getBestDiscount());
        carRepository.save(car);
        return ResponseEntity.ok(true);
    }

    public void setBestDiscountOnCar(int id, int value){
        Car car = getCarById(id);
        car.setBestDiscount(value);
        carRepository.save(car);
    }

    public void deleteCarById(int id) {
        Car car = getCarById(id);
        carRepository.delete(car);
    }

    private Car getCarById(int id){
        return carRepository.findById(id).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with ID does not exist"));
    }
}
