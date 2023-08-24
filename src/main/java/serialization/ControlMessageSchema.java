package serialization;

import models.ControlMessage;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlMessageSchema implements SerializationSchema<ControlMessage> {

    private final static Logger log = LoggerFactory.getLogger(ControlMessageSchema.class);

    @Override
    public void open(InitializationContext context) throws Exception {
        SerializationSchema.super.open(context);
    }

    @Override
    public byte[] serialize(ControlMessage controlMessage) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String value = mapper.writeValueAsString(controlMessage);
            return value.getBytes();
        } catch (Exception ex) {
            log.error("Failed to write controlMessage ({}) as bytes", controlMessage);
            return null;
        }
    }
}
