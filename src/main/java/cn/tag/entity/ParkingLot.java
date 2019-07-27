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
    private int id;

    @Column(nullable = false, length = 25)
    private String parkingName;

    private int parkingLotCapacity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "staffId")
    private ParkingStaff parkingStaff;

}
