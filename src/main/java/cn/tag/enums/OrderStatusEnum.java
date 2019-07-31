package cn.tag.enums;

public enum OrderStatusEnum {

    PARKING_WAIT("PW", "无人受理"),
    PARKING_ING("PI", "存取中"),
    WAIT_TO("WT","等待停车受理"),
    //    HAVE_PARKING("HP","已停车"),
    FETCH_WAIT("FW","等待取车受理"),
//    FETCH_ING("FI","取车中"),
    GO_ING("GI", "前往地点"),
    PAY_WAIT("WP", "待支付"),
    FINISH("F", "已完成"),
    CANCEL("C", "取消订单");

    private String description;

    private String key;


    OrderStatusEnum(String key, String description) {
        this.description = description;
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
