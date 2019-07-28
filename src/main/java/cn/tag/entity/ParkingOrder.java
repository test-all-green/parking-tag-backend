package cn.tag.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column(length = 25)
    private String carNumber;

    private Integer parkingOrderType;

    private Integer parkingOrderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parkingId")
    private ParkingLot parkingLot;

    private Date parkingOrderCreateTime;

    private Date parkingOrderEndTime;

}
