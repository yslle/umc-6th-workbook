package umc.spring.service.StoreService;

import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.StoreRequestDTO;

public interface StoreCommandService {
    Store joinStore(StoreRequestDTO.JoinDto request);

    Review createReview(StoreRequestDTO.CreateReviewDto request, Long storeId, Long memberId);

    Mission createMission(StoreRequestDTO.CreateMissionDto request, Long storeId);

    Long deleteReview(Long reviewId);
}
