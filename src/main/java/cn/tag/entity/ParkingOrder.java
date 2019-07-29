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
    private int Id;

    private String carNum;//车牌
    private Integer carUserId;//车主
    private Date parkingCreate_Time;//停车开始时间
    private Date parkingEndTime;//停车结束时间
    private Integer parkingBoyId;//停车服务人
    private String parkingWaitLocation;//停车取车点
    private String parkingLocation;//停车地点
    private String scheduledParkingArriveTime;//停车预计到达时间
    private Integer fetchBoyId;//取车服务人
    private String fetchWaitLocation;//取车拿车点
    private Long scheduledFetchArriveTime;//取车预计到达时间
    private Long scheduledParkingTime;//预计停车时长
    private Long createTime;//创建时间
    private Long endTime;//结束时间
    private String status;//状态（停车无受理人WP、停车中PI、已停车HP、取车无人受理WF、取车中FI、完成F）
    private Double money;//整个订单钱
    private Double parkingMoney;//停车钱
    private Double fetchMoney;//取车钱

}
