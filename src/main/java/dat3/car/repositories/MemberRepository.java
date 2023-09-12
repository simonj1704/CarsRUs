package dat3.car.repositories;

import dat3.car.dto.MemberResponse;
import dat3.car.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {

    List<Member> getMembersByReservationsNotNull();

}
