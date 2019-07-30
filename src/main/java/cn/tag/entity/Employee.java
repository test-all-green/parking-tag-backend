package cn.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String employeeId;//工号
    private String employeeName;
    private String email;
    private String employeePassword;
    private String phone;
    private String workStatus;
    private Integer status; //账号状态，0正常，1注销
    private Double money;
    private Integer roleId;
    private Integer regionId;

    public Employee(String employeeId, String employeeName, String email, String employeePassword, String phone, String workStatus, Integer status, Integer roleId, Integer regionId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.employeePassword = employeePassword;
        this.phone = phone;
        this.workStatus = workStatus;
        this.status = status;
        this.roleId = roleId;
        this.regionId = regionId;
    }
}
