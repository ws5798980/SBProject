package com.rs.mobile.wportal.biz.xsgr;

import java.io.Serializable;
import java.util.List;

public class PushImg implements Serializable{

    /**
     * status : 1
     * data : ["/uploadfiles/92d9a8ff-4aeb-46a3-928a-d7c7ce933539.jpg"]
     */

    private String status;
    private List<String> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
