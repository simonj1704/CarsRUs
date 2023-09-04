package dat3.car.service;

import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Member;
import dat3.car.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberServiceH2Test {

    @Autowired
    MemberRepository memberRepository;
    MemberService memberService;

    Member m1, m2;

    @BeforeEach
    void setUp(){
        m1 = memberRepository.save(new Member("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1"));
        m2 = memberRepository.save(new Member("user2", "pw2", "fn2", "ln2", "email2", "street2", "city2", "zip2"));
        memberService = new MemberService(memberRepository);
    }

    @Test
    void testGetMembersAllDetails(){
        List<MemberResponse> responses = memberService.getMembers(true);
        assertEquals(2, responses.size(), "Expected 2 members");
        LocalDateTime created = responses.get(0).getCreated();
        assertNotNull(created, "Dates must be set since true was passed to getMembers");
    }

    @Test
    void testGetMembersNoDetails(){
        List<MemberResponse> responses = memberService.getMembers(false);
        assertEquals(2, responses.size(), "Expected 2 members");
        LocalDateTime created = responses.get(0).getCreated();
        assertNull(created, "Dates must be null since true was passed to getMembers");
    }

    @Test
    void testFindByIdFound(){
        MemberResponse response = memberService.findById(m1.getUsername());
        assertEquals(m1.getUsername(), response.getUsername());
    }

    @Test
    void testFindByIdNotFound(){
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> memberService.findById("user3"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testAddMember_UserDoesExist(){
        MemberRequest request = MemberRequest.builder().
                username("user1").
                password("pw3").
                firstName("fn3").
                lastName("ln3").
                build();
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> memberService.addMember(request));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
    }

    @Test
    void testAddMember_UserDoesNotExist(){
        MemberRequest request = MemberRequest.builder().
                username("user3").
                password("pw3").
                firstName("fn3").
                lastName("ln3").
                build();
        MemberResponse response = memberService.addMember(request);
        assertEquals("user3", response.getUsername());
        assertTrue(memberRepository.existsById("user3"));
    }

    @Test
    void testEditMemberWithExistingUsername(){
        MemberRequest request = new MemberRequest(m1);
        request.setFirstName("newFirstName");
        request.setLastName("newLastName");

        memberService.editMember(request, m1.getUsername());

        memberRepository.flush();
        MemberResponse response = memberService.findById(m1.getUsername());
        assertEquals("user1", response.getUsername());
        assertEquals("newFirstName", response.getFirstName());
        assertEquals("newLastName", response.getLastName());
        assertEquals("email1", response.getEmail());
        assertEquals("street1", response.getStreet());
        assertEquals("city1", response.getCity());
        assertEquals("zip1", response.getZip());

    }

    @Test
    void testEditMemberWithNON_ExistingUsername(){
        MemberRequest request = new MemberRequest();
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> memberService.editMember(request, "I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void testEditMemberChangePrimaryKey(){
        MemberRequest request = new MemberRequest(m1);
        request.setUsername("newUsername");
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> memberService.editMember(request, m1.getUsername()));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        assertEquals("Cannot change username", ex.getReason());
    }

    @Test
    void testRankingForUser(){
        memberService.setRankingForUser(m1.getUsername(), 5);
        MemberResponse response = memberService.findById(m1.getUsername());
        assertEquals(5, response.getRanking());
    }

    @Test
    void testDeleteMemberByUsername(){
        memberService.deleteMemberByUsername(m1.getUsername());
        assertFalse(memberRepository.existsById(m1.getUsername()));
    }

    @Test
    void testDeleteMember_ThatDontExist(){
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> memberService.deleteMemberByUsername("I dont exist"));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }
}
