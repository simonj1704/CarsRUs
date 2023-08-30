package dat3.car.api;

import dat3.car.dto.CarRequest;
import dat3.car.dto.CarResponse;
import dat3.car.dto.MemberResponse;
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


    //Security: Admin Only
    @GetMapping
    List<CarResponse> getCars(){ return carService.getCars(false);}

    //Security Admin Only
    @GetMapping(path = "/{id}")
    CarResponse getCarById(@PathVariable int id) throws Exception {
        return carService.findById(id);
    }

    //Security --> Anonymous
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CarResponse addCar(@RequestBody CarRequest body){
        return carService.addCar(body);
    }

    //Security Admin
    @PutMapping("/{id}")
    ResponseEntity<Boolean> editMember(@RequestBody CarRequest body, @PathVariable int id){
        return carService.editMember(body, id);
    }

    //Security Admin
    @DeleteMapping("/{id}")
    boolean deleteCarById(@PathVariable int id) {
        return carService.deleteCarById(id);
    }
}
