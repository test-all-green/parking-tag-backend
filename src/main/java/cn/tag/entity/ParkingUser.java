package cn.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingUser {
    private String staffName;

    private String staffPhnoe;

    private String staffEmail;

    private int staffStatus;

    private int staffAccountStatus;

    private String staffPassword;
}
