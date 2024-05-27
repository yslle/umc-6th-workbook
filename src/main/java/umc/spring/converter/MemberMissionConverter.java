package umc.spring.converter;

import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MemberResponseDTO;

public class MemberMissionConverter {

    public static MemberResponseDTO.MemberMissionResultDTO toCreateMemberMissionResultDTO(MemberMission memberMission){
        return MemberResponseDTO.MemberMissionResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .build();
    }
}
