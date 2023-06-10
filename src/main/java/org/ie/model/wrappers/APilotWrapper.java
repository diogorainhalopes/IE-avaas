package org.ie.model.wrappers;

import org.ie.model.APilot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APilotWrapper {
    @JsonProperty("APilot")
    private APilot apilot;

    public APilotWrapper(APilot apilot) {
        this.apilot = apilot;
    }

    public APilotWrapper() {

    }

    public String toJson() {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ow.toString();
    }

    public APilot getApilot() {
        return apilot;
    }

    public void setApilot(APilot apilot) {
        this.apilot = apilot;
    }
}
