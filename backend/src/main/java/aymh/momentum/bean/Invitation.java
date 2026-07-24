package aymh.momentum.bean;

import aymh.momentum.security.bean.User;
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
public class Invitation {
    @Id
    @SequenceGenerator(name = "invitation_sequence", sequenceName = "invitation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "invitation_sequence")
    private Long id;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

}
