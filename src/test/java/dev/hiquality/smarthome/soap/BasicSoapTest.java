package dev.hiquality.smarthome.soap;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class BasicSoapTest {

    private final SoapClient client = new SoapClient();
    private final SmartHomePortType port = client.createSoapClient();

    @Test
    void basicSoapTest() {
        GetDevicesResponse response = port.getDevices(new GetDevicesRequest());
        assertThat(response.getDeviceList().getDevice(), is(not(empty())));
    }

}
