package com.example.ipfsmaven.config;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Config {
    private String envScheduledUpdateStatus;
    private String envScheduledUploadNodes;
    private String envLocalIpfsNode;
    private String envTimeoutRequestFromNode;

    @PostConstruct
    public void init() {
        this.envLocalIpfsNode = System.getenv("IPFS_LOCAL_NODE");
        this.envScheduledUpdateStatus = System.getenv("IPFS_SCHEDULED_UPDATE_STATUS");
        this.envScheduledUploadNodes = System.getenv("IPFS_SCHEDULED_UPLOAD_NODES");
        this.envTimeoutRequestFromNode = System.getenv("IPFS_TIMEOUT_REQUEST_FROM_NODE");
    }
    public String getEnvLocalIpfsNode() {
        return Objects.requireNonNullElse(envLocalIpfsNode, "/dns4/ipfs/tcp/5001");
    }
    public Integer getEnvTimeoutRequestFromNode() {
        if (envTimeoutRequestFromNode == null) {
            return 2;
        }
        return Integer.parseInt(envTimeoutRequestFromNode);
    }



    public Integer getEnvScheduledUpdateStatus() {
        if (envScheduledUpdateStatus == null) {
            return 10;
        }
        return Integer.parseInt(envScheduledUpdateStatus);
    }

    public Integer getEnvScheduledUploadNodes() {
        if (envScheduledUploadNodes == null) {
            return 10;
        }
        return Integer.parseInt(envScheduledUploadNodes);
    }


}
