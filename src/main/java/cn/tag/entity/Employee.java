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
    private String telephone;
    private String workStatus;
    private Integer status; //账号状态，0正常，1注销
    private Double moneny;

}
