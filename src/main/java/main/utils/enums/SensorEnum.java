package main.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum SensorEnum {
    SENSOR_1("1","zigbee2mqtt/sensor1","sensor1","DOOR_SENSOR");

    public String id;
    public String address;
    public String name;
    public String description;

    SensorEnum(final String id,
               final String address,
               final String name,
               final String description) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.description = description;
    }

    public static List<SensorEnum> getSensorList (){
        return Arrays.stream(SensorEnum.values()).collect(Collectors.toList());
    }
}
