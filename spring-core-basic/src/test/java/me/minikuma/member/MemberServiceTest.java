package me.minikuma.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    @DisplayName("회원 가입 정상 테스트")
    void join() {
        // given
        Member member = new Member(1L, "memberB", Grade.VIP);
        // when
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());
        // then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
