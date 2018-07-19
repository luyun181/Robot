package com.robot.robotcontrol.event;

public class NetSuccessEvent {
    private String url;

    public NetSuccessEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
