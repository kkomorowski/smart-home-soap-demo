package dev.hiquality.smarthome.soap;

import dev.hiquality.smarthome.soap.service.SmartHomeServiceImpl;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class SmartHomeServiceServer {
    public static void main(String[] args) {
        String address = "http://localhost:8080/smart-home-service";

        // Create the service implementation instance
        SmartHomeServiceImpl serviceImpl = new SmartHomeServiceImpl();

        // Create the server factory bean
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setServiceClass(SmartHomePortType.class);
        factoryBean.setAddress(address);
        factoryBean.setServiceBean(serviceImpl);
        factoryBean.getFeatures().add(new LoggingFeature());
        factoryBean.getHandlers().add(new SoapHandler());

        // Create the server and start it
        factoryBean.create();
        System.out.println("Smart Home Service is running at: " + address);
    }
}
