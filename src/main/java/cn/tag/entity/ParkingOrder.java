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

    private Date parkingOrderCreateTime;

    private Date parkingOrderEndTime;

    private Integer parkingLotId;

    private Integer parkingStaffId;

    public ParkingOrder(String carNumber, Integer parkingOrderType, Integer parkingOrderStatus, Date parkingOrderCreateTime, Date parkingOrderEndTime, Integer parkingLotId, Integer parkingStaffId) {
        this.carNumber = carNumber;
        this.parkingOrderType = parkingOrderType;
        this.parkingOrderStatus = parkingOrderStatus;
        this.parkingOrderCreateTime = parkingOrderCreateTime;
        this.parkingOrderEndTime = parkingOrderEndTime;
        this.parkingLotId = parkingLotId;
        this.parkingStaffId = parkingStaffId;
    }
}
