package umc.spring.converter;

import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.StoreRequestDTO;
import umc.spring.web.dto.StoreResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class StoreConverter {

    // store
    public static StoreResponseDTO.JoinResultDTO toJoinResultDTO(Store store) {
        return StoreResponseDTO.JoinResultDTO.builder()
                .storeId(store.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Store toStore(StoreRequestDTO.JoinDto request) {
        return Store.builder()
                .name(request.getName())
                .category(request.getCategory())
                .address(request.getAddress())
                .operatingHours(request.getOperatingHours())
                .missionList(new ArrayList<>())
                .build();
    }

    // review
    public static StoreResponseDTO.CreateReviewResultDTO toCreateReviewResultDTO(Review review) {
        return StoreResponseDTO.CreateReviewResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Review toReview(StoreRequestDTO.CreateReviewDto request){
        return Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .reviewImageList(new ArrayList<>())
                .build();
    }
}
