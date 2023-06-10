package org.ie.model.wrappers;

import org.ie.model.AvResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvResultWrapper {
    @JsonProperty("AV_RESULT")
    private AvResult avResult;

    public AvResult getAvResult() {
        return avResult;
    }

    public AvResultWrapper() {
        // Used by Jackson deserialization
    }

    public AvResultWrapper(AvResult avResult) {
        this.avResult = avResult;
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

}
