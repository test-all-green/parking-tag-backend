package cn.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String parkingLotName;
    private Long beginTime;
    private Long endTime;
    private Integer status; //0: 未发布，1: 已发布，2: 使用中

    private Integer userId;
    private Integer regionId;
//    private String location; //地址
    private Integer locationId; //关联ShareParkingLotLocation表
    private String locationName;
    private Double price = 5.0; //共享车位价格

}
