package cn.tag.enums;

public enum EmployeeStatusEnum {

    NORMAL(0,"正常"),
    UNUSUAL(1,"异常");

    private Integer key;
    private String description;


    EmployeeStatusEnum(Integer key, String description) {
        this.description = description;
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }
}
