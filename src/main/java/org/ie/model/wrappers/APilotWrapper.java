package org.ie.model.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
