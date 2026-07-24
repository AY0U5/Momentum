package aymh.momentum.bean;

import aymh.momentum.security.common.enums.ColumnNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KanbanColumn {
    @Id
    @SequenceGenerator(name = "kanban_sequence", sequenceName = "kanban_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "kanban_sequence")
    private Long id;
    @Enumerated(EnumType.STRING)
    private ColumnNames label;
    private BigDecimal position;
    private String color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
