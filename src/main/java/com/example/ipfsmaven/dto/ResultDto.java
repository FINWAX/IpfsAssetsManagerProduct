package com.example.ipfsmaven.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDto {
    List<String> clients;
    String returnText;
    String error;
}
