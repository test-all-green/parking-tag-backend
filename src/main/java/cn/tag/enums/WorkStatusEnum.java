package cn.tag.enums;

public enum WorkStatusEnum {

    BUSY("B","忙碌"),
    IDLE("IL","空闲");

    private String key;

    private String description;

    WorkStatusEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
