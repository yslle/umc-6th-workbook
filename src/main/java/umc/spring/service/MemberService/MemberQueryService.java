package umc.spring.service.MemberService;

import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findMember(Long id);

    Optional<MemberMission> findMemberMissionByMemberAndMission(Long memberId, Long missionId);

    Page<Review> getMyReviewList(Long memberId, Integer page);

    Page<MemberMission> getMyChallengingList(Long memberId, Integer page);
}
