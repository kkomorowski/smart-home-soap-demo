package dev.hiquality.smarthome.soap.service;

import dev.hiquality.smarthome.soap.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SmartHomeServiceImpl implements SmartHomePortType {

    private final List<Device> devices = new ArrayList<>(
            List.of(
                deviceType(DeviceType.WINDOW, UUID.fromString("cafebabe-0000-0000-0000-000000000001"), "Window #1", Status.OPEN, "CAFEBABE"),
                deviceType(DeviceType.DOOR, UUID.fromString("cafebabe-0000-0000-0000-000000000002"), "Door #1", Status.CLOSED, "BABECAFE")
            ));

    private Device deviceType(DeviceType type, UUID uuid, String name, Status status, String secret) {
        var device = new Device();
        device.setType(type);
        device.setID(uuid.toString());
        device.setName(name);
        device.setStatus(status);
        device.setSecret(secret.getBytes());
        return device;
    }

    private static Device hideSecret(Device d) {
        d.setSecret(null);
        return d;
    }

    public GetDevicesResponse getDevices(GetDevicesRequest parameters) {
        GetDevicesResponse response = new GetDevicesResponse();
        var dev = new GetDevicesResponse.DeviceList();
        if (parameters.getType() != null) {
            dev.getDevice().addAll(devices.stream()
                    .filter(d -> d.getType() == parameters.getType())
                    .map(SmartHomeServiceImpl::hideSecret)
                    .toList());
        }
        else {
            dev.getDevice().addAll(devices.stream().map(SmartHomeServiceImpl::hideSecret).toList());
        }
        response.setDeviceList(dev);
        return response;
    }

    @Override
    public RegisterDeviceResponse registerDevice(RegisterDeviceRequest parameters) {
        var device = parameters.getDevice();
        var response = new RegisterDeviceResponse();
        if (devices.stream().filter(d -> d.getID().equals(device.getID())).findFirst().isEmpty()) {
            devices.add(device);
            response.setExecutionStatus(ExecutionStatus.OK);
            response.setMessage("Device added!");
        }
        else {
            response.setExecutionStatus(ExecutionStatus.FAILED);
            response.setMessage("Device with id: " + device.getID() + " already present!");
        }
        return response;
    }
}
