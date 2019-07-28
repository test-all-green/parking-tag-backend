package cn.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StaffCharacter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String characteName;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ParkingPermission> permissionList;

    public StaffCharacter(String characteName) {
        this.characteName = characteName;
    }
}
