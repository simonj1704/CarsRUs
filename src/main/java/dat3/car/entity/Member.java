package dat3.car.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//------------------
@Entity
@Table(name="member")
public class Member {
    @Id
    String username;
    String email;
    String password;
    String firstName;
    String lastName;
    String street;
    String city;
    String zip;
    boolean approved;
    int ranking;

    public Member(String user, String password, String email, String firstName,
                  String lastName, String street, String city, String zip) {
        this.username = user;
        this.password= password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.zip = zip;
    }

}
