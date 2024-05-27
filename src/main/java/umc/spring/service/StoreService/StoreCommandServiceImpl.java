package umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.RegionHandler;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.*;
import umc.spring.repository.*;
import umc.spring.web.dto.StoreRequestDTO;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    @Override
    public Store joinStore(StoreRequestDTO.JoinDto request) {

        // region 조회
        Region region = regionRepository.findByName(request.getRegion())
                .orElseThrow(() -> new RegionHandler(ErrorStatus.REGION_NOT_FOUND));

        // store 저장
        Store newStore = StoreConverter.toStore(request);
        newStore.setRegion(region);

        return storeRepository.save(newStore);
    }

    @Override
    public Review createReview(StoreRequestDTO.CreateReviewDto request, Long storeId, Long memberId) {
        Review newReview = StoreConverter.toReview(request);
        newReview.setMember(memberRepository.findById(memberId).get());
        newReview.setStore(storeRepository.findById(storeId).get());

        return reviewRepository.save(newReview);
    }

    @Override
    public Mission createMission(StoreRequestDTO.CreateMissionDto request, Long storeId) {
        // store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));

        Mission newMission = StoreConverter.toMission(request);
        newMission.setStore(store);

        return missionRepository.save(newMission);
    }

}
