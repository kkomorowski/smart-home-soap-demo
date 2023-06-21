package dev.hiquality.smarthome.soap.service;

import dev.hiquality.smarthome.soap.*;

import java.util.List;
import java.util.UUID;

public class SmartHomeServiceImpl implements SmartHomePortType {

    private DeviceType deviceType(DeviceTypeEnum type, UUID uuid, String name, StatusEnum status) {
        var device = new DeviceType();
        device.setType(type);
        device.setID(uuid.toString());
        device.setName(name);
        device.setStatus(status);
        return device;
    }

    private final List<DeviceType> devices = List.of(
            deviceType(DeviceTypeEnum.WINDOW, UUID.fromString("cafebabe-0000-0000-0000-000000000001"), "Window #1", StatusEnum.OPEN),
            deviceType(DeviceTypeEnum.DOOR, UUID.fromString("cafebabe-0000-0000-0000-000000000002"), "Door #1", StatusEnum.CLOSED)
    );

    public GetSmartHomeDevicesResponse getSmartHomeDevices(GetSmartHomeDevicesRequest parameters) {
        GetSmartHomeDevicesResponse response = new GetSmartHomeDevicesResponse();
        if (parameters.getType() != null) {
            response.getDevices().addAll(
                    devices.stream()
                            .filter(d -> d.getType() == parameters.getType())
                            .toList()
            );
        }
        else {
            response.getDevices().addAll(devices);
        }
        return response;
    }
}
