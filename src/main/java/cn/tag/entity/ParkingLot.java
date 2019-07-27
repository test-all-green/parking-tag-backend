package cn.tag.entity;

import javax.persistence.*;
import java.io.Serializable;

public class ParkingLot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String parkingName;

    private int parkingLotCapacity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staffIdgit")
    private ParkingStaff parkingStaff;
}
