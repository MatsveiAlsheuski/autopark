package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import lombok.*;

@Table(name = "orders")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ID
    private Long id;
    @Column(name = "details")
    private String details;
    @Column(name = "numberOfBreakdowns")
    private Integer numberOfBreakdowns;
}
