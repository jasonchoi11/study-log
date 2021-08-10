package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제 발생할 수 있음
 */
public class MemberRepository {
    private Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // Singleton
    private static final MemberRepository instance = new MemberRepository();

    // Singleton privat 생성자
    private MemberRepository() {
    }

    public static MemberRepository getInstance() {
        return instance;
    }

    // save
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // find id
    public Member findById(Long id) {
        return store.get(id);
    }

    // find all
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // test 용 Store clear
    public void clearStore() {
        store.clear();
    }
}
