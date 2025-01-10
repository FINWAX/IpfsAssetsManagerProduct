package com.example.ipfsmaven.controller;

import com.example.ipfsmaven.dto.ResponseDto;
import com.example.ipfsmaven.service.impl.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileController {
    private final FileService service;

    @GetMapping("/setPackage")
    public void setPackageCount(){
        service.setPackageCount();
    }

    @PostMapping("/addFile")
    public ResponseEntity<?> saveFile(@RequestPart MultipartFile file){
        try {
            return ResponseEntity.ok()
                    .body(service.getCidAndSaveFile(file));
        }catch (RuntimeException | IOException e){
           return ResponseEntity.ok()
               .body(new ResponseDto(true,e.getMessage()));
        }
    }

}
