package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repositories.CarRepository;
import dat3.car.repositories.MemberRepository;
import dat3.car.repositories.ReservationRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DeveloperData implements ApplicationRunner {

    CarRepository carRepository;
    MemberRepository memberRepository;
    ReservationRepository reservationRepository;

    public DeveloperData(CarRepository carRepository, MemberRepository memberRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Car> cars = generateCars();
        List<Member> members = generateMembers();
        carRepository.saveAll(cars);
        memberRepository.saveAll(members);

        Car car1 = new Car("VW", "Golf", 760, 25);
        Member m1 = new Member("Jan","test12","Morten","Jan","Jensen","Lyngbyvej 1","Lyngby","2800");
        carRepository.save(car1);
        memberRepository.save(m1);

        LocalDate date1start = LocalDate.now().plusDays(2);
        LocalDate date1end = LocalDate.now().plusDays(4);
        LocalDate date2start = LocalDate.now().plusDays(5);
        LocalDate date2end = LocalDate.now().plusDays(7);

        Reservation reservation1 = new Reservation(date1start, date1end, 760, car1, m1);
        Reservation reservation2 = new Reservation(date2start, date2end, 760, car1, m1);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        System.out.println("xxxx ------> "+car1.getReservations().size());
        System.out.println("xxxx ------> "+m1.getReservations().size());

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

