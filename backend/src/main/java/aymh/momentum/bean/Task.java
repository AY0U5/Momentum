package aymh.momentum.bean;

import aymh.momentum.security.common.enums.TaskPriority;
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
public class Task {

    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "task_sequence")
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private LocalDateTime dueDate;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    @ManyToOne()
    @JoinColumn(name = "column_id")
    private KanbanColumn column; // state
}
