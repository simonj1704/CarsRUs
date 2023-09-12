package dat3.car.api;

import dat3.car.dto.ReservationRequest;
import dat3.car.dto.ReservationResponse;
import dat3.car.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    ReservationService reservationService;

    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }


    //Security: ADMIN
    @GetMapping
    public List<ReservationResponse> getReservations(){
        return reservationService.getReservations();
    }

    @GetMapping(path = "/{id}")
    public ReservationResponse getReservationById(@PathVariable int id){
        return reservationService.findById(id);
    }

    @GetMapping(path = "/member/{id}")
    public List<ReservationResponse> getReservationsByMemberId(@PathVariable String id){
        return reservationService.getReservationsByMemberId(id);
    }


    //Security: ADMIN
    @PostMapping
    public ReservationResponse makeReservation(@RequestBody ReservationRequest body){
        ReservationResponse r = reservationService.reserveCar(body);
        return r;
    }
}
