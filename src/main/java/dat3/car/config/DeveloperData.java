package dat3.car.config;

import dat3.car.entity.Car;
import dat3.car.entity.Member;
import dat3.car.entity.Reservation;
import dat3.car.repositories.CarRepository;
import dat3.car.repositories.MemberRepository;
import dat3.car.repositories.ReservationRepository;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import dat3.security.repository.UserWithRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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



    public DeveloperData(CarRepository carRepository, MemberRepository memberRepository, ReservationRepository reservationRepository) {
        this.carRepository = carRepository;
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Member> members = MemberTestDataFactory.generateTestMembers("test12",3);
        memberRepository.saveAll(members);
        List<Car> cars = CarTestDataFactory.generateTestCars();
        carRepository.saveAll(cars);

        Car car1 = new Car("VW", "Golf", 760, 25);
        Member m1 = new Member("Jan","test12","Jan","Jensen","a@b.dk","Lyngbyvej 1","Lyngby","2800");
        carRepository.save(car1);
        memberRepository.save(m1);

        LocalDate date1 = LocalDate.now().plusDays(2);
        LocalDate date2 = LocalDate.now().plusDays(3);
        Reservation r1 = new Reservation(date1, car1, m1);
        Reservation r2 = new Reservation(date2, car1, m1);
        reservationRepository.save(r1);
        reservationRepository.save(r2);

        setupUserWithRoleUsers();
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

    private List<Member> generateMembers(int numberOfMembers) {
        List<Member> members = new ArrayList<>();
        Random random = new Random();

        String[] firstNames = {"John", "Jane", "Michael", "Emily", "David", "Sarah"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Miller", "Wilson", "Davis"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Houston", "Miami", "Seattle"};
        String[] zipCodes = {"12345", "67890", "54321", "98765", "43210"};

        for (int i = 0; i < numberOfMembers; i++) {
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

    @Autowired
    UserWithRolesRepository userWithRolesRepository;

    final String passwordUsedByAll = "test12";

    /*****************************************************************************************
     NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
     iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO
     *****************************************************************************************/
    private void setupUserWithRoleUsers() {

        System.out.println("******************************************************************************");
        System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
        System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
        System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
        System.out.println("******************************************************************************");
        UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
        UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
        UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
        UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
        user1.addRole(Role.USER);
        user1.addRole(Role.ADMIN);
        user2.addRole(Role.USER);
        user3.addRole(Role.ADMIN);
        //No Role assigned to user4
        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);
        userWithRolesRepository.save(user3);
        userWithRolesRepository.save(user4);
    }

}

