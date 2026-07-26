package aymh.momentum.ws.converter;

import aymh.momentum.bean.Project;
import aymh.momentum.ws.dto.MemberDto;
import aymh.momentum.ws.dto.ProjectDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectConverter {


    public Project toBean(ProjectDto dto) {
        if (dto == null) {
            return null;
        }
        return Project.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .state(dto.getState())
                .build();
    }

    public ProjectDto toDto(Project bean) {
        if (bean == null) {
            return null;
        }
        return ProjectDto.builder()
                .id(bean.getId())
                .title(bean.getTitle())
                .description(bean.getDescription())
                .createdAt(bean.getCreatedAt())
                .state(bean.getState())
                .build();
    }

    public ProjectDto toDto(
            Project bean,
            boolean pinned ,
            long memberCount,
            List<MemberDto> members
    ) {
        if (bean == null) {
            return null;
        }
        return ProjectDto.builder()
                .id(bean.getId())
                .title(bean.getTitle())
                .description(bean.getDescription())
                .createdAt(bean.getCreatedAt())
                .state(bean.getState())
                .pinned(pinned)
                .memberCount(memberCount)
                .members(members)
                .build();
    }
}
