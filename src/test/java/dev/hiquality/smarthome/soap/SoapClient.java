package dev.hiquality.smarthome.soap;


import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;

import javax.xml.namespace.QName;

public class SoapClient {

    SmartHomePortType createSoapClient() {
        Service service = Service.create(
                getClass().getClassLoader().getResource("wsdl/smart_home_demo.wsdl"),
                new QName("https://soap.hiquality.dev/smarthome", "SmartHomeService", "")
        );
        SmartHomePortType port = service.getPort(SmartHomePortType.class);
        BindingProvider bind = (BindingProvider) port;
        bind.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://localhost:8080/smart-home-service");
        return port;
    }

}
