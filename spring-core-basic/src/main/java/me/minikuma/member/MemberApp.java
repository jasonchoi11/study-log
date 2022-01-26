package me.minikuma.member;

public class MemberApp {
    // 스프링 프레임워크 없이 테스트하기 위한 클래스
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(memberA.getId());
        // 가입된 멤버와 가입된 멤버 아이디로 조회했을 때 동일
        System.out.println("findMember = " + findMember.getName());
        System.out.println("New member = " + memberA.getName());
        System.out.println("same equals = " + findMember.getName().equals(memberA.getName()));
    }
}