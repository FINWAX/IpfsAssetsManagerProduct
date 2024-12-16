package com.example.ipfsmaven.config;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Config {
    final private String envScheduledUpdateStatus = System.getenv("IPFS_SCHEDULED_UPDATE_STATUS");
    final private String envScheduledUploadNodes = System.getenv("IPFS_SCHEDULED_UPLOAD_NODES");
    final private String envLocalIpfsNode = System.getenv("IPFS_LOCAL_NODE");
    final private String envTimeoutRequestFromNode = System.getenv("IPFS_TIMEOUT_REQUEST_FROM_NODE");

    public String getEnvLocalIpfsNode() {
        return Objects.requireNonNullElse(envLocalIpfsNode, "/ip4/127.0.0.1/tcp/5001");
    }
    public Integer getEnvTimeoutRequestFromNode() {
        if (envTimeoutRequestFromNode == null) {
            return 2;
        }
        return Integer.parseInt(envTimeoutRequestFromNode);
    }



    public Integer getEnvScheduledUpdateStatus() {
        if (envScheduledUpdateStatus == null) {
            return 1;
        }
        return Integer.parseInt(envScheduledUpdateStatus);
    }

    public Integer getEnvScheduledUploadNodes() {
        if (envScheduledUploadNodes == null) {
            return 1;
        }
        return Integer.parseInt(envScheduledUploadNodes);
    }



}
