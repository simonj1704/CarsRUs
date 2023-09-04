package dat3.car.service;

import dat3.car.dto.MemberRequest;
import dat3.car.dto.MemberResponse;
import dat3.car.entity.Member;
import dat3.car.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceMockitoTest {

    //@InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    private Member makeMember(String username, String password, String firstName, String lastName, String email, String street, String city, String zip) {
        Member member = new Member(username, password, firstName, lastName, email, street, city, zip);
        member.setCreated(LocalDateTime.now());
        member.setEdited(LocalDateTime.now());
        return member;
    }

    private Member makeMember2() {
        Member m2 = new Member("user2", "pw2", "fn2", "ln2", "email2", "street2", "city2", "zip2");
        m2.setCreated(LocalDateTime.now());
        m2.setEdited(LocalDateTime.now());
        return m2;
    }

    @Test
    public void testGetMembers(){
        // Define a mock behavior
        Member m1 = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        Member m2 = makeMember("user2", "pw2", "fn2", "ln2", "email2", "street2", "city2", "zip2");
        when(memberRepository.findAll()).thenReturn(List.of(m1, m2));
        List<MemberResponse> responses = memberService.getMembers(true);
        // Assertions
        assertEquals(2, responses.size(), "Expected 2 members");
        assertNotNull(responses.get(0).getCreated(), "Dates must be set since true was passed to getMembers");
    }

    @Test
    public void testFindById(){
        when(memberRepository.findById("user2")).thenReturn(Optional.of(makeMember2()));
        MemberResponse response = memberService.findById("user2");
        assertEquals("user2", response.getUsername());
        assertNotNull(response.getRanking(), "Expected ranking to be set, since true was used inside findById");
    }

    //tests for memberService class methods
    @Test
    public void testAddMember_UserExists(){
        when(memberRepository.existsById("user1")).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> {
            Member existingMember = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
            //Make a MemberRequest with a username that already exists
            MemberRequest mr = new MemberRequest(existingMember);
            memberService.addMember(mr);
        });
    }

    @Test
    public void testAddMember_Success(){
        Member newMember = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        MemberRequest mr = new MemberRequest(newMember);
        when(memberRepository.save(newMember)).thenReturn(newMember);
        MemberResponse response = memberService.addMember(mr);
        assertEquals("user1", response.getUsername());
    }
}
