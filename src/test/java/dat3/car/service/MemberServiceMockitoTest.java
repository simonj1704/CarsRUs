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
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
    public void testGetMembers() {
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
    public void testFindById() {
        when(memberRepository.findById("user2")).thenReturn(Optional.of(makeMember2()));
        MemberResponse response = memberService.findById("user2");
        assertEquals("user2", response.getUsername());
        assertNotNull(response.getRanking(), "Expected ranking to be set, since true was used inside findById");
    }

    //tests for memberService class methods
    @Test
    public void testAddMember_UserExists() {
        when(memberRepository.existsById("user1")).thenReturn(true);
        assertThrows(ResponseStatusException.class, () -> {
            Member existingMember = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
            //Make a MemberRequest with a username that already exists
            MemberRequest mr = new MemberRequest(existingMember);
            memberService.addMember(mr);
        });
    }

    @Test
    public void testAddMember_Success() {
        Member newMember = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        when(memberRepository.existsById("user1")).thenReturn(false);
        when(memberRepository.save(any(Member.class))).thenReturn(newMember);
        MemberRequest mr = new MemberRequest(newMember);
        MemberResponse response = memberService.addMember(mr);
        assertEquals("user1", response.getUsername());
    }

    @Test
    public void testEditMember_CannotChangeUsername() {
        Member newMember = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        Member changedMember = makeMember("user2", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        MemberRequest mr2 = new MemberRequest(changedMember);
        when(memberRepository.findById("user1")).thenReturn(Optional.of(newMember));
        assertThrows(ResponseStatusException.class, () -> {
            memberService.editMember(mr2, "user1");
        });
    }

    @Test
    public void testEditMember_Success() {
        Member newMember = makeMember("user1", "pw1", "fn1", "ln1", "email1", "street1", "city1", "zip1");
        MemberRequest mr = new MemberRequest(newMember);
        mr.setFirstName("newFirstName");
        mr.setLastName("newLastName");

        when(memberRepository.findById("user1")).thenReturn(Optional.of(newMember));

        memberService.editMember(mr, "user1");

        assertEquals("user1", newMember.getUsername());
        assertEquals("newFirstName", newMember.getFirstName());
        assertEquals("newLastName", newMember.getLastName());
        assertEquals("pw1", newMember.getPassword());
        assertEquals("email1", newMember.getEmail());
        assertEquals("street1", newMember.getStreet());
        assertEquals("city1", newMember.getCity());
        assertEquals("zip1", newMember.getZip());
    }

    @Test
    public void testDeleteMemberByUsername() {
        String testUsername = "testUser";
        Member testMember = new Member();
        testMember.setUsername(testUsername);

        // Mock the behavior of memberRepository to return the testMember when findById is called with testUsername.
        when(memberRepository.findById(testUsername)).thenReturn(Optional.of(testMember));

        // Call the method under test.
        memberService.deleteMemberByUsername(testUsername);

        // Verify that memberRepository's delete method was called with the testMember.
        verify(memberRepository).delete(testMember);
    }

    @Test
    public void testDeleteMemberByUsername_MemberNotFound(){
        String testUsername = "testUser";

        when(memberRepository.findById(testUsername)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> memberService.deleteMemberByUsername(testUsername));
    }

    @Test
    public void testSetRankingForUser(){
        Member m1 = new Member();
        when(memberRepository.findById("user2")).thenReturn(Optional.of(m1));
        int testRanking = 5;

        // Mock the behavior of memberRepository to save the testMember.
        when(memberRepository.save(m1)).thenReturn(m1);
        memberService.setRankingForUser("user2", testRanking);
        assertEquals(testRanking, m1.getRanking());

        // Verify that memberRepository's save method was called with the testMember.
        verify(memberRepository).save(m1);
    }
}
