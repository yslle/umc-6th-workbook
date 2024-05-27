package umc.spring.service.MemberService;

import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findMember(Long id);

    Optional<MemberMission> findMemberMissionByMemberAndMission(Long memberId, Long missionId);
}
