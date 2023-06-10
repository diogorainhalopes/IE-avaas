package org.ie.model.wrappers;

import org.ie.model.AvEvent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvEventWrapper {
    @JsonProperty("AV_Event")
    private AvEvent avEvent;

    public AvEvent getAvEvent() {
        return avEvent;
    }

    public void setAvEvent(AvEvent avEvent) {
        this.avEvent = avEvent;
    }

    public AvEventWrapper() {

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

    // Getter and setter for avEvent
}
