package umc.spring.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.MemberService.MemberQueryService;
import umc.spring.validation.annotation.IsChallengingMission;
import umc.spring.web.dto.MemberRequestDTO;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MissionChallengingValidator implements ConstraintValidator<IsChallengingMission, MemberRequestDTO.DoMission> {

    private final MemberQueryService memberQueryService;

    @Override
    public void initialize(IsChallengingMission constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(MemberRequestDTO.DoMission value, ConstraintValidatorContext context) {
        Optional<MemberMission> target = memberQueryService.findMemberMissionByMemberAndMission(value.getMemberId(), value.getMissionId());

        if (target.isPresent() && target.get().getStatus() == MissionStatus.CHALLENGING){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
            return false;
        }
        return true;
    }
}