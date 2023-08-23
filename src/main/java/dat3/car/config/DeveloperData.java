package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.repositories.CarRepository;
import dat3.car.repositories.MemberRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class DeveloperData implements ApplicationRunner {

    CarRepository carRepository;
    MemberRepository memberRepository;

    public DeveloperData(CarRepository carRepository, MemberRepository memberRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        carRepository.saveAll(generateCars());
        memberRepository.saveAll(generateMembers());
    }

    private List<Car> generateCars() {
        List<String> carBrands = List.of(
                "Toyota", "Honda", "Ford", "Chevrolet", "Nissan",
                "BMW", "Mercedes-Benz", "Audi", "Volkswagen", "Volvo",
                "Hyundai", "Kia", "Mazda", "Subaru", "Jeep"
        );

        List<String> carModels = List.of(
                "Camry", "Civic", "Focus", "Malibu", "Altima",
                "3 Series", "E-Class", "A4", "Golf", "XC90",
                "Elantra", "Soul", "CX-5", "Outback", "Wrangler"
        );
        List<Integer> discounts = List.of(2, 5, 10, 15, 20, 25, 30, 35, 40, 45);

        List<Car> cars = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            String brand = carBrands.get(random.nextInt(carBrands.size()));
            String model = carModels.get(random.nextInt(carModels.size()));
            int price = 200 + random.nextInt(800); // Price range: 200 - 999
            Integer bestDiscount = discounts.get(random.nextInt(discounts.size()));

            Car car = new Car();
            car.setBrand(brand);
            car.setModel(model);
            car.setPricePrDay(price);
            car.setBestDiscount(bestDiscount);
            cars.add(car);
        }
        return cars;
    }

    private List<Member> generateMembers() {
        List<Member> members = new ArrayList<>();
        Random random = new Random();

        String[] firstNames = {"John", "Jane", "Michael", "Emily", "David", "Sarah"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Miller", "Wilson", "Davis"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Miami", "Seattle"};
        String[] zipCodes = {"12345", "67890", "54321", "98765", "43210"};

        for (int i = 0; i < 10; i++) {
            String username = "user" + (random.nextInt(1000) + 1);
            String password = "pass" + (random.nextInt(1000) + 1);
            String email = username + "@example.com";
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String street = "Street" + (random.nextInt(100) + 1);
            String city = cities[random.nextInt(cities.length)];
            String zip = zipCodes[random.nextInt(zipCodes.length)];

            Member member = new Member(username, password, email, firstName, lastName, street, city, zip);
            members.add(member);
        }

        return members;
    }
}

