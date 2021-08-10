package hello.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberRepositoryTest {
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void clear() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);

        // when
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveMember.getId());

        // then
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("hello1", 4);
        Member member2 = new Member("hello2", 6);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> findMembers = memberRepository.findAll();
        // then
        assertThat(findMembers.size()).isEqualTo(2);
        assertThat(findMembers).contains(member1, member2);
    }
}