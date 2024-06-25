package umc.spring.service.StoreService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.RegionHandler;
import umc.spring.apiPayload.exception.handler.StoreHandler;
import umc.spring.aws.s3.AmazonS3Manager;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.*;
import umc.spring.repository.*;
import umc.spring.web.dto.StoreRequestDTO;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;
    private final RegionRepository regionRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;
    private final UuidRepository uuidRepository;
    private final AmazonS3Manager s3Manager;
    private final ReviewImageRepository reviewImageRepository;

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
        Review review = StoreConverter.toReview(request);

        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        String pictureUrl = s3Manager.uploadFile(s3Manager.generateReviewKeyName(savedUuid), request.getReviewPicture());

        review.setMember(memberRepository.findById(memberId).get());
        review.setStore(storeRepository.findById(storeId).get());


        reviewImageRepository.save(StoreConverter.toReviewImage(pictureUrl,review));
        return reviewRepository.save(review);
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
