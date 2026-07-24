package aymh.momentum.bean;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    @Id
    @SequenceGenerator(name = "membership_sequence", sequenceName = "membership_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "membership_sequence")
    private Long id;
    private LocalDateTime joinedAt;
    @Enumerated(EnumType.STRING)
    private ProjectRole role;
    private boolean active;
    private boolean pinned;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
