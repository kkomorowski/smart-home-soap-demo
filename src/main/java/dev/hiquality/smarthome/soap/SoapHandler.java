package dev.hiquality.smarthome.soap;

import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

public class SoapHandler implements Handler<SOAPMessageContext> {


    private Schema getSchema() {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Source[] schemas = {
                new StreamSource(getClass().getClassLoader().getResourceAsStream("wsdl/smart_home_demo.xsd")),
                new StreamSource(getClass().getClassLoader().getResourceAsStream("wsdl/rps-soap-11.xsd"))
            };
            sf.setFeature("http://apache.org/xml/features/honour-all-schemaLocations", true);
            return sf.newSchema(schemas);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
        try {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            messageContext.getMessage().writeTo(os);
            var schema = getSchema();
            var message = new StreamSource(new StringReader(os.toString()));
            assert schema != null;
            schema.newValidator().validate(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {

    }
}
