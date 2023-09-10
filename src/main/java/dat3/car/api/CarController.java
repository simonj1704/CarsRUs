package dat3.car.api;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
public class CarController {
    CarService carService;

    public CarController (CarService carService){
        this.carService = carService;
    }


    //Security: Anonymous
    @GetMapping
    List<CarResponse> getCars(){ return carService.getCars(false);}

    //Security Anonymous
    @GetMapping(path = "/{id}")
    CarResponse getCarById(@PathVariable int id) throws Exception {
        return carService.findById(id, true);
    }

    //Security Anonymous
    @GetMapping(path = "/brand/{brand}/model/{model}")
    List<CarResponse> getCarByBrandAndModel(@PathVariable String brand, @PathVariable String model){
        return carService.findCarByBrandAndModel(brand, model);
    }

    //Security Anonymous
    @GetMapping(path = "/available")
    List<CarResponse> getAvailableCars(){
        return carService.findAvailableCars();
    }

    //Security ADMIN
    @GetMapping(path = "/bestDiscount")
    List<CarResponse> getCarsByBestDiscount(){
        return carService.getCarsByBestDiscount(true);
    }

    //Security --> Anonymous
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body){
        return carService.addCar(body);
    }

    //Security Admin
    @PutMapping("/{id}")
    ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int id){
        return carService.editCar(body, id);
    }

    //Security Admin
    @PatchMapping("/price/{id}/{newPrice}")
    void setPrice(@PathVariable int id, @PathVariable double newPrice) {
        carService.setPrice(id, newPrice);
    }

    //Security ADMIN
    @PatchMapping("/ranking/{id}/{value}")
    public void setBestDiscountOnCar(@PathVariable int id, @PathVariable int value){
        carService.setBestDiscountOnCar(id,value);
    }

    //Security Admin
    @DeleteMapping("/{id}")
    void deleteCarById(@PathVariable int id) {
        carService.deleteCarById(id);
    }
}
