package seon.full.mallapi.service;

import org.springframework.transaction.annotation.Transactional;
import seon.full.mallapi.domain.Member;
import seon.full.mallapi.dto.MemberDTO;
import seon.full.mallapi.dto.MemberModifyDTO;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDTO getKakakoMember(String accessToken);

    void modifyMember(MemberModifyDTO memberModifyDTO);

    default MemberDTO entityToDTO(Member member) {
        MemberDTO dto = new MemberDTO(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()
                ));
        return dto;
    }
}
