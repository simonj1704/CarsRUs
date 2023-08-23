package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.repositories.CarRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class DeveloperData implements ApplicationRunner {

    CarRepository carRepository;

    public DeveloperData(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{
        System.out.println("I was called");
        // An array of car brands and models
        String[] cars = {"Toyota Corolla", "Honda Civic", "Ford F-150", "Chevrolet Silverado", "Tesla Model 3",
                "Volkswagen Golf", "Hyundai Elantra", "Nissan Sentra", "BMW 3 Series",
                "Mercedes-Benz C-Class", "Audi A4", "Volvo S60", "Mazda 3", "Subaru Outback",
                "Kia Soul", "Honda CR-V", "Toyota RAV4", "Ford Escape", "Nissan Rogue", "Hyundai Tucson",
                "Jeep Wrangler", "Land Rover Range Rover", "Porsche Cayenne", "Lexus RX", "Acura MDX",
                "Fiat 500", "Mini Cooper", "Smart Fortwo", "Renault Clio", "Peugeot 208", "Citroen C3",
                "Opel Corsa", "Skoda Fabia", "Seat Ibiza", "Dacia Sandero", "Alfa Romeo Giulia",
                "Ferrari 488", "Lamborghini Huracan", "Maserati Ghibli", "Aston Martin DB11",
                "Bentley Continental GT", "Rolls-Royce Phantom", "Bugatti Chiron", "Koenigsegg Agera R",
                "Pagani Huayra BC","Honda Accord","Toyota Camry","Chevrolet Malibu","Ford Fusion",
                "Nissan Altima"};

// A list to store the Car objects
        List<Car> carList = new ArrayList<>();

// A random number generator
        Random random = new Random();

// A loop to create 50 Car objects
        for (int i = 0; i < 50; i++) {
            // Split the car brand and model by space
            String[] carInfo = cars[i].split(" ");
            // Create a new Car object with random values for rental_price_day and max_discount
            Car car = new Car();
            car.setBrand(carInfo[0]);
            car.setModel(carInfo[1]);
            car.setPricePrDay(Math.round((random.nextDouble() * 100 + 50) * 100) / 100.0); // A random value between 50 and 150
            car.setBestDiscount(random.nextInt(50) + 10); // A random value between 10 and 60
            // Add the Car object to the list
            carList.add(car);
        }
        carRepository.saveAll(carList);
    }

}
