package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.MemberMissionRepository;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.ReviewRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Optional<Member> findMember(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<MemberMission> findMemberMissionByMemberAndMission(Long memberId, Long missionId) {
        return memberMissionRepository.findByMemberIdAndMissionId(memberId, missionId);
    }

    @Override
    public Page<Review> getMyReviewList(Long memberId, Integer page) {
        Member member = findMember(memberId).get();
        Page<Review> StorePage = reviewRepository.findAllByMember(member, PageRequest.of(page, 10));
        return StorePage;
    }

    @Override
    public Page<MemberMission> getMyChallengingList(Long memberId, Integer page) {
        Member member = findMember(memberId).get();
        Page<MemberMission> StorePage = memberMissionRepository.findAllByMemberAndStatus(member, MissionStatus.CHALLENGING, PageRequest.of(page, 10));
        return StorePage;
    }
}