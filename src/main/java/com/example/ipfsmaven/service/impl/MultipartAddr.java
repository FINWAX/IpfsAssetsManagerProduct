package com.example.ipfsmaven.service.impl;

import lombok.Getter;
import lombok.ToString;

import java.net.URI;
import java.net.URISyntaxException;

@Getter
@ToString
public class MultipartAddr {
    private String protocol;
    private String host;
    private String login;
    private String hashPass;
    private int port;

    public MultipartAddr(String link) {
        parseLink(link);
    }

    private void parseLink(String link)  {
        URI uri;
        try {
            uri = new URI(link);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        this.protocol = uri.getScheme();
        this.host = uri.getHost();
        this.login = uri.getUserInfo() != null ? uri.getUserInfo().split(":")[0] : null;
        this.hashPass = uri.getUserInfo() != null ? uri.getUserInfo().split(":")[1] : null;
        this.port = uri.getPort() != -1 ? uri.getPort() : (protocol.equals("https") ? 443 : 80);
    }


}


