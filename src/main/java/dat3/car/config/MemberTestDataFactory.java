package dat3.car.config;

import dat3.car.entity.Member;
import dat3.security.entity.Role;

import java.util.ArrayList;
import java.util.List;

/*
mainly generated by ChatGPT using this prompt:
I hava a Spring Boot Data JPA application with an Entity with this constructor:
public Member(String user, String password, String email,
                String firstName, String lastName, String street, String city, String zip) {
and a Member Repository.
Can you provide 50 test members.
Username should be user1, user2 ... and
password allways test12
The remaining parts should include real names and danish addresses
 */
public class MemberTestDataFactory {
    public static List<Member> generateTestMembers(String passwordUsedByAll, int numberOfMembers) {
        List<Member> members = new ArrayList<>();

        String[] firstNames = {"Anders", "Bent", "Christine", "Dorte", "Erik", "Freja", "Gustav", "Helle", "Inge", "Jens"};
        String[] lastNames = {"Nielsen", "Jensen", "Hansen", "Pedersen", "Andersen", "Christensen", "Larsen", "Sørensen", "Rasmussen", "Petersen"};
        String[] streets = {"Bredgade", "Kongens Nytorv", "Østergade", "Nørregade", "Vesterbrogade", "Amagertorv", "Frederiksborggade", "Strøget", "Gothersgade", "Rosenborggade"};
        String[] cities = {"København", "Aarhus", "Odense", "Aalborg", "Esbjerg", "Randers", "Kolding", "Horsens", "Vejle", "Roskilde"};
        String[] zips = {"1000", "8000", "5000", "9000", "6700", "8900", "6000", "8700", "7100", "4000"};

        for (int i = 1; i <= numberOfMembers; i++) {
            String user = "member" + i;
            String password = passwordUsedByAll;
            String email = user + "@testmail.com";
            String firstName = firstNames[i % 10];
            String lastName = lastNames[i % 10];
            String street = streets[i % 10] + " " + (i % 50 + 1);
            String city = cities[i % 10];
            String zip = zips[i % 10];

            Member member = new Member(user, password, firstName, lastName, email, street, city, zip);
            member.addRole(Role.USER);
            members.add(member);
        }
        return members;
    }
}
