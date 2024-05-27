package umc.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import umc.spring.validation.annotation.ExistCategories;

import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDto {
        @NotBlank
        String name;
        @NotNull
        String address;
        @NotNull
        String phone;
        @NotNull
        Integer gender;
        @ExistCategories
        List<Long> preferCategory;
    }
}