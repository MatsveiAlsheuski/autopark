package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import lombok.*;


@Table(name = "types")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Types {
    @ID
    private Long id;
    @Column(name = "typeName", unique = true)
    private String typeName;
    @Column(name = "taxCoefficient")
    private Double taxCoefficient;

}
