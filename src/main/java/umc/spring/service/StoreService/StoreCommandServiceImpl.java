package umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.RegionHandler;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Region;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.MemberRepository;
import umc.spring.repository.RegionRepository;
import umc.spring.repository.ReviewRepository;
import umc.spring.repository.StoreRepository;
import umc.spring.web.dto.StoreRequestDTO;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    
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
    public Review createReview(StoreRequestDTO.CreateReviewDto request, Long storeId) {
        // store 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreHandler(ErrorStatus.STORE_NOT_FOUND));

        Review newReview = StoreConverter.toReview(request);
        // 하드코딩 된 사용자 사용
        String name = "승연";
        Member member = memberRepository.findByName(name);
        newReview.setMember(member);
        newReview.setStore(store);

        return reviewRepository.save(newReview);
    }

}
