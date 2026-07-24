package aymh.momentum.bean;

import aymh.momentum.security.bean.User;
import aymh.momentum.security.common.enums.NotificationTitle;
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
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_sequence", sequenceName = "notification_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "notification_sequence")
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationTitle title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
}
