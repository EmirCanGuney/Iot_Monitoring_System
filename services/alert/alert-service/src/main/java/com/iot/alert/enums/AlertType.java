package com.iot.alert.enums;

public enum AlertType {
    HIGH_TEMPERATURE("High Temperature"),
    LOW_TEMPERATURE("Low Temperature"),
    HIGH_HUMIDITY("High Humidity"),
    LOW_HUMIDITY("Low Humidity");

    private final String description;

    AlertType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
