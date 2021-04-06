package com.osj.stockinfomation.util;

import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;

public class PushPayloadDAO implements Serializable {

    private String title = "";
     private String contents = "";

     private String linkUrl = "";

    public PushPayloadDAO(RemoteMessage remoteMessage)
    {
        title      = remoteMessage.getData().get("title");
        contents   = remoteMessage.getData().get("body");
        linkUrl    = remoteMessage.getData().get("link_url");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

}
