package com.whisper.client.model;

public class notification {

    private String type ;
    private String icorSrc ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcorSrc() {
        return icorSrc;
    }

    public void setIcorSrc(String icorSrc) {
        this.icorSrc = icorSrc;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    private String fromUserName ;

}
