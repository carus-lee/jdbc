package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save
        Member member = new Member("memberV9", 10000);
        Member result = repositoryV0.save(member);
        Assertions.assertThat(result).isEqualTo(member);

        //findById
        Member findMember = repositoryV0.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember={}", member == findMember);
        log.info("member equals findMember={}", member.equals(findMember));
        Assertions.assertThat(findMember).isEqualTo(member);

        //update: money: 10000 -> 20000
        repositoryV0.update(member.getMemberId(), 20000);
        log.info("before updateMember={}", member);
        Member updateMember = repositoryV0.findById(member.getMemberId());
        log.info("after updateMember={}", updateMember);
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        repositoryV0.delete(member.getMemberId());
        /*
         * DB에서 삭제되어 memberId가 조회 안됨 (error 발생)
         * 따라서 Assertions.assertThatThrownBy()를 사용하여 예외 인스턴스로 검증한다.
         */
//        Member deleteMember = repositoryV0.findById(member.getMemberId());
        Assertions.assertThatThrownBy(() -> repositoryV0.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}