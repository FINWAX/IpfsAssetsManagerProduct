package com.example.ipfsmaven.controller;

import com.example.ipfsmaven.dto.NodeDto;
import com.example.ipfsmaven.dto.ResponseDto;
import com.example.ipfsmaven.model.HistoryIPFS;
import com.example.ipfsmaven.model.ListNodesIPFS;
import com.example.ipfsmaven.service.impl.HistoryService;
import com.example.ipfsmaven.service.impl.NodesService;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class NodeController {
    private final NodesService nodesService;
    private final HistoryService historyService;


    @PostMapping(value="/addNode",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveNode(@Valid @RequestBody NodeDto nodeDto) {
        nodesService.addNewNode(nodeDto);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true)), HttpStatus.CREATED);
    }

    @GetMapping("/getNodes")
    public ResponseEntity<List<ListNodesIPFS>> getListNodes(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                            @RequestParam(value = "count", defaultValue = "100") Integer count){
        return ResponseEntity.ok()
                .body(nodesService.getAllNodes(offset, count));
    }
    @GetMapping("/getHistory")
    public ResponseEntity<List<HistoryIPFS>> getHistory(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                        @RequestParam(value = "count", defaultValue = "100") Integer count){
        return ResponseEntity.ok()
                .body(historyService.getAllHistory(offset, count));
    }

    @GetMapping("/removeNode/{id}")
    public ResponseEntity<?> removeNode(@PathVariable Long id) {
        nodesService.deleteById(id);
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true)), HttpStatus.NO_CONTENT);
    }


    @GetMapping("/removeUnavailableNodes")
    public ResponseEntity<?> removeUnavailableNodes() {
        nodesService.removeUnavailableNodes();
        return new ResponseEntity<>(new Gson().toJson(new ResponseDto(true)), HttpStatus.NO_CONTENT);
    }

}
