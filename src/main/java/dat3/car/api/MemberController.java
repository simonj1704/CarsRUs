package dat3.car.api;

import dat3.car.entity.Member;
import dat3.car.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/members")
class MemberController {

    @Autowired  //Deliberately added via Autowired, remove this endpoint when you know why it's bad
    MemberRepository memberRepository;
    @GetMapping("/bad")
    public List<Member> getMembersBad(){
        return memberRepository.findAll();
    }
}


