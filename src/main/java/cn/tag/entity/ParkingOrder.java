package cn.tag.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ParkingOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    private String carNum;//车牌
    private Integer carUserId;//车主
    private Long parkingCreateTime;//停车开始时间
    private Long parkingEndTime;//停车结束时间
    private Integer parkingBoyId;//停车服务人
    private String parkingWaitLocation;//交接点
    private String parkingLotId;
    private String parkingLocation;//停车地点
    private String scheduledParkingArriveTime;//预计到达时间
    private Long scheduledParkingTime;//预计停车时长
    private Long createTime;//创建时间
    private Long endTime;//结束时间
    private String status;//状态（无人受理WP、存取中PI、完成F，前往地点GI，待支付WP，取消订单C）
    private Double money;//整个订单钱
    private Integer type;//类型：0停车，1取车
    private Integer regionId; //区域ID
    @Version
    private Integer version;

}
