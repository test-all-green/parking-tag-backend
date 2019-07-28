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
public class ParkingPermission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, length = 25)
    private String permissionName;

    @Column(length = 100)
    private String permissionDescription;
}
