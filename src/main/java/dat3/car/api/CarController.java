package dat3.car.api;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/cars")
public class CarController {
    CarService carService;

    public CarController (CarService carService){
        this.carService = carService;
    }


    //Security: Anonymous
    @GetMapping
    List<CarResponse> getCars(){ return carService.getCars(false);}

    //Security: Admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    List<CarResponse> getCarsAll(){ return carService.getCars(true);}


    //Security Anonymous
    @GetMapping(path = "/{id}")
    CarResponse getCarById(@PathVariable int id) throws Exception {
        return carService.findById(id, false);
    }

    //Security: Admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/admin/{id}")
    CarResponse getCarByIdAll(@PathVariable int id) throws Exception {
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/bestDiscount")
    List<CarResponse> getCarsByBestDiscount(){
        return carService.getCarsByBestDiscount(true);
    }

    //Security ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/averagePrice")
    double getAveragePricePrDay(){
        return carService.getAveragePricePrDay();
    }

    //Security --> Anonymous
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body){
        return carService.addCar(body);
    }

    //Security Admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<Boolean> editCar(@RequestBody CarRequest body, @PathVariable int id){
        return carService.editCar(body, id);
    }

    //Security Admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/price/{id}/{newPrice}")
    void setPrice(@PathVariable int id, @PathVariable double newPrice) {
        carService.setPrice(id, newPrice);
    }

    //Security ADMIN
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/ranking/{id}/{value}")
    public void setBestDiscountOnCar(@PathVariable int id, @PathVariable int value){
        carService.setBestDiscountOnCar(id,value);
    }

    //Security Admin
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    void deleteCarById(@PathVariable int id) {
        carService.deleteCarById(id);
    }
}
