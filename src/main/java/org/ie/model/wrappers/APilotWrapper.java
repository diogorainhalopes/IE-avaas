package org.ie.model.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class APilotWrapper {

    private int avid;

    private int apilotid;

    public int getApilotid() {
        return apilotid;
    }

    public void setApilotid(int apilotid) {
        this.apilotid = apilotid;
    }

    public int getAvid() {
        return avid;
    }

    public void setAvid(int avid) {
        this.avid = avid;
    }

    public APilotWrapper(@JsonProperty("avid") int avid, @JsonProperty("apilotid") int apilotid) {
        this.avid = avid;
        this.apilotid = apilotid;
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
