package cn.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 25)
    private String parkingName;

    private Integer parkingLotCapacity;

    /**
     * 1: 已注销 0: 未注销
     */
    @Column(precision = 0)
    private Integer parkingLotStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staffId")
    private ParkingStaff parkingStaff;

    public ParkingLot(String parkingName, Integer parkingLotCapacity, ParkingStaff parkingStaff) {
        this.parkingName = parkingName;
        this.parkingLotCapacity = parkingLotCapacity;
        this.parkingStaff = parkingStaff;
    }
}
