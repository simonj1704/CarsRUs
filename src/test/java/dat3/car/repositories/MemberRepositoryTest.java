package dat3.car.repositories;

import dat3.car.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    boolean isInitialized = false;

    @BeforeEach
    public void setUp(){
        memberRepository.deleteAll();
        memberRepository.save(new Member("user1", "pass1", "John", "Doe", "user1@example.com", "123 Main St", "City1", "12345"));
        memberRepository.save(new Member("user2", "pass2", "Jane", "Smith", "user2@example.com", "456 Elm St", "City2", "67890"));
        memberRepository.save(new Member("user3", "pass3", "Michael", "Johnson", "user3@example.com", "789 Oak St", "City3", "54321"));
        isInitialized = true;
    }


    @Test
    public void testAll(){
        assertEquals(3, memberRepository.count());
    }

    @Test
    public void deleteAll(){
        memberRepository.deleteAll();
        assertEquals(0, memberRepository.count());
    }

    @Test
    public void testFindById(){
        Member member = memberRepository.findById("user1").get();
        assertEquals("John", member.getFirstName());
    }


}