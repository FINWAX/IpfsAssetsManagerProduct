package com.example.ipfsmaven.service;//package com.example.ipfsmaven.service;
//
//import com.example.ipfsmaven.config.Config;
//import com.example.ipfsmaven.service.impl.NodesService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//
//@Service
//@EnableScheduling
//public class ScheduledStatusService {
//    private final NodesService nodesService;
//    private final Config config;
//    protected Long timeupdatedelay;
//    protected Long timeuploaddelay;
//
//    public ScheduledStatusService(NodesService nodesService, Config config) {
//        this.nodesService = nodesService;
//        this.config = config;
//        this.timeupdatedelay = Long.valueOf(config.getEnvScheduledUpdateStatus())*1000*60;
//        this.timeuploaddelay = Long.valueOf(config.getEnvScheduledUploadNodes())*1000*60;
//    }
//
//    /**
//     * Обновление статуса ноды
//     */
//    @Scheduled(fixedRateString = "${scheduled.update.status.timeupdatedelay:30000}")
//    public void getUpdatesScheduled() {
//        System.out.println("GO "+new Date());
//        this.timeupdatedelay = Long.valueOf(config.getEnvScheduledUpdateStatus())*1000*60;
//        nodesService.SchedulerUpdateStatuses();
//    }
//
//
//    /**
//     * Загрузка на ноды
//     */
//    @Scheduled(fixedRateString = "${scheduled.update.status.timeuploaddelay:30000}")
//    public void getUpdatesScheduledUploadNodes() {
//        this.timeuploaddelay = Long.valueOf(config.getEnvScheduledUploadNodes());
//        nodesService.NodesUpdate();
//    }
//}

import com.example.ipfsmaven.config.Config;
import com.example.ipfsmaven.service.impl.NodesService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@EnableScheduling
public class ScheduledStatusService {
    private final NodesService nodesService;
    private final Config config;
    protected Long timeupdatedelay;
    protected Long timeuploaddelay;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ScheduledStatusService(NodesService nodesService, Config config) {
        this.nodesService = nodesService;
        this.config = config;
        updateDelays();
    }

    @PostConstruct
    public void startScheduling() {
        scheduler.scheduleAtFixedRate(this::getUpdatesScheduled, timeupdatedelay, timeupdatedelay, TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(this::getUpdatesScheduledUploadNodes, timeuploaddelay, timeuploaddelay, TimeUnit.MILLISECONDS);
    }

    private void updateDelays() {
        this.timeupdatedelay = Long.valueOf(config.getEnvScheduledUpdateStatus()) * 1000 * 60;
        this.timeuploaddelay = Long.valueOf(config.getEnvScheduledUploadNodes()) * 1000 * 60;
    }

    public void getUpdatesScheduled() {
        updateDelays();
        log.info("scheduledUpdate " +new Date());
        nodesService.SchedulerUpdateStatuses();
    }

    public void getUpdatesScheduledUploadNodes() {
        updateDelays();
        log.info("scheduledUpload " +new Date());
        nodesService.NodesUpdate();
    }
}
