package cn.tag.service;

import cn.tag.entity.Employee;
import cn.tag.entity.ParkingOrder;
import cn.tag.entity.Region;
import cn.tag.entity.User;
import cn.tag.enums.OrderStatusEnum;
import cn.tag.exception.CustomException;
import cn.tag.respository.EmployeeRepository;
import cn.tag.respository.ParkingOrderRepository;
import cn.tag.respository.RegionRepository;
import cn.tag.respository.UserRepository;
import cn.tag.util.TokenUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingOrderService {
    public static final String CODE_401 = "401";
    public static final String ORDER_ERROR = "您的车有未完成订单";
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    public List<ParkingOrder> findAll() {
        return parkingOrderRepository.findAll();
    }

    public ParkingOrder add(ParkingOrder parkingOrder) {
        String carNum = parkingOrder.getCarNum();
        if (parkingOrderRepository.findOrdersByCarNumAndStatusNotF(carNum).size() == 0) {
            parkingOrder.setCarUserId(Integer.valueOf(TokenUtil.getTokenUserId()));
            parkingOrder.setCreateTime(System.currentTimeMillis());
            parkingOrder.setStatus(OrderStatusEnum.PARKING_WAIT.getKey());
            return parkingOrderRepository.save(parkingOrder);
        } else {
            throw new CustomException(CODE_401, ORDER_ERROR);
        }
    }

    private String getStringDate() {
        Date current = new Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "yyyyMMddHHmmss");
        String time = sdf.format(current);
        return time;
    }

    public Page<ParkingOrder> findByPage(Integer page, Integer pageSize) {
        return parkingOrderRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Transactional
    public ParkingOrder update(Integer id, ParkingOrder parkingOrder) {
        parkingOrder.setId(id);
        return parkingOrderRepository.save(parkingOrder);
    }

    public List<ParkingOrder> findOrderOfUser(Integer userId) {
        return parkingOrderRepository.findByCarUserIdOrderByCreateTimeDesc(userId);
    }

    public ParkingOrder findOrderByOrderId(Integer orderId) {
//        return parkingOrderRepository.findAll()
        return parkingOrderRepository.findById(orderId).get();
    }

    public List<ParkingOrder> findOrderByCarUserId(Integer carUserId) {
        return parkingOrderRepository.findByCarUserId(carUserId);
    }

    private JSONArray getOrderJsonArray(List<ParkingOrder> orderList) {
        JSONArray jsonArray = new JSONArray();
        orderList.forEach(parkingOrder -> {
            JSONObject jsonObject = getOrderJsonObject(parkingOrder);
            jsonArray.add(jsonObject);
        });
        return jsonArray;

    }

    private JSONObject getOrderJsonObject(ParkingOrder parkingOrder) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", parkingOrder.getId());
        jsonObject.put("carNum", parkingOrder.getCarNum());
        jsonObject.put("carUserId", parkingOrder.getCarUserId());
        jsonObject.put("parkingCreateTime", parkingOrder.getParkingCreateTime());
        jsonObject.put("parkingEndTime", parkingOrder.getParkingEndTime());
        jsonObject.put("parkingBoyId", parkingOrder.getParkingBoyId());
        jsonObject.put("parkingWaitLocation", parkingOrder.getParkingWaitLocation());
        jsonObject.put("parkingLocation", parkingOrder.getParkingLocation());
        jsonObject.put("scheduledParkingArriveTime", parkingOrder.getScheduledParkingArriveTime());
        jsonObject.put("scheduledParkingTime", parkingOrder.getScheduledParkingTime());
        jsonObject.put("createTime", parkingOrder.getCreateTime());
        jsonObject.put("endTime", parkingOrder.getEndTime());
        jsonObject.put("status", parkingOrder.getStatus());
        jsonObject.put("money", parkingOrder.getMoney());
        jsonObject.put("type", parkingOrder.getType());
        jsonObject.put("regionId", parkingOrder.getRegionId());
        Region region = regionRepository.findById(parkingOrder.getRegionId()).get();
        jsonObject.put("regionName", region.getRegionName());
        User user = userRepository.findById(parkingOrder.getCarUserId()).get();
        jsonObject.put("phone", user.getPhone());
        return jsonObject;
    }

    public JSONArray getOrdersWithStatus(String status) {
        List<ParkingOrder> ordersWithStatus = parkingOrderRepository.getOrdersWithStatus(status);
        return getOrderJsonArray(ordersWithStatus);
    }

    public JSONArray findByEmployeeIdOrderByCreateTime(Integer employeeId) {
        List<ParkingOrder> ordersWithStatus = parkingOrderRepository.findByEmployeeIdOrderByCreateTime(employeeId);
        return getOrderJsonArray(ordersWithStatus);
    }

    public JSONObject getOrderWithStyleIsZeroAndStatusIsF(Integer userId) {
        JSONObject jsonObject = new JSONObject();
        ParkingOrder parkingOrder = parkingOrderRepository.getOrderWithStyleIsZeroAndStatusIsF(userId);
        if (parkingOrder != null) {
            jsonObject = getOrderJsonObject(parkingOrder);
        } else {
            jsonObject.put("massage", "您未停车！！");
        }
        return jsonObject;
    }

    public JSONArray findByUserLocation() {
        String employeeId = TokenUtil.getTokenUserId();
        JSONObject jsonObject = new JSONObject();
        Employee employee = employeeRepository.findById(Integer.valueOf(employeeId)).get();
        List<ParkingOrder> ordersWithStatus = parkingOrderRepository.findByUserLocation(employee.getRegionId());
        return getOrderJsonArray(ordersWithStatus);
    }

    public Map getOrdersByOrderIdAndType(Integer orderId, Integer type) {
        Map map = new HashMap();
        ParkingOrder parkingOrder = parkingOrderRepository.findById(orderId).get();
        JSONObject jsonObject = new JSONObject();
        jsonObject = getOrderJsonObject(parkingOrder);
        map.put("parkOrder",jsonObject);
        if(type==0){
            map.put("fetchOrder",null);
        }else if(type == 1){
            ParkingOrder parkingOrder1 = parkingOrderRepository.findOrderByPreOrderId(parkingOrder.getPreviousOrderId());
            jsonObject = getOrderJsonObject(parkingOrder1);
            map.put("fetchOrder",jsonObject);
        }
        return map;
    }
}
