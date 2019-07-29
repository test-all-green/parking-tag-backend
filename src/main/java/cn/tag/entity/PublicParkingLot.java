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
public class PublicParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 25)
    private String parkingLotName;

    private Integer parkingLotCapacity;

    private String location;

    private Integer remain;
    private Integer regionId;
    private Integer status;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private int id;
//
//    @Column(nullable = false, length = 25)
//    private String parkingName;
//
//    private int parkingLotCapacity;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "staffId")
//    private ParkingStaff parkingStaff;
//
//    public PublicParkingLot(String parkingName, int parkingLotCapacity, ParkingStaff parkingStaff) {
//        this.parkingName = parkingName;
//        this.parkingLotCapacity = parkingLotCapacity;
//        this.parkingStaff = parkingStaff;
//    }
}
