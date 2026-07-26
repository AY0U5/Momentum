package aymh.momentum.ws.converter;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;
import aymh.momentum.ws.dto.MemberDto;
import org.springframework.stereotype.Component;


@Component
public class MemberConverter {

    public MemberDto toMember(User user, ProjectRole role){
        if (user == null) {
            return null;
        }
        return MemberDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .picture(user.getPicture())
                .role(role)
                .build();
    }

    public User toUser(MemberDto member){
        if (member == null) {
            return null;
        }
        return User.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .picture(member.getPicture())
                .build();
    }
}
