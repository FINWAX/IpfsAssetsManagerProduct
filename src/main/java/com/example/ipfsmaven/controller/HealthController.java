package com.example.ipfsmaven.controller;

import com.example.ipfsmaven.config.Config;
import com.example.ipfsmaven.dto.ResponseDto;
import com.example.ipfsmaven.service.impl.NodesService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/ipfs/health")
@RequiredArgsConstructor
public class HealthController {
    private final NodesService nodesService;
    private final Config config;


    @CrossOrigin
    @GetMapping(value = "/availability",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAvailability(){
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true)), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(value = "/check",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCheck(){
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(nodesService.pingStatusNode(config.getEnvLocalIpfsNode()))), HttpStatus.OK);
    }


}