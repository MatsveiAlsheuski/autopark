package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import lombok.*;

import java.util.Date;

@Table(name = "rents")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Rents {
    @ID
    private Long id;
    @Column(name = "date")
    private Date date;
    @Column(name = "rentCost")
    private Double rentCost;
}

