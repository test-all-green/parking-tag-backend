package cn.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "parking_staff")
public class ParkingStaff  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 25)
    private String staffName;

    @Column(nullable = false, length = 11)
    private String staffPhnoe;

    @Column(nullable = false, length = 25)
    private String staffEmail;

    private int staffStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="characterId")
    private StaffCharacter staffCharacter;

    private int staffAccountStatus;

    private String staffPassword;

//    @OneToMany(fetch = FetchType.EAGER)
//
//    private List<ParkingStaff> staffList;
//    private int pid;
}
