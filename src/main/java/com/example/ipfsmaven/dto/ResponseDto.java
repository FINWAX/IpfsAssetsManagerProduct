package com.example.ipfsmaven.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private boolean ok;
    private ResultDto result;

    public ResponseDto(boolean ok, ResultDto result) {
        this.ok = ok;
        this.result = result;
    }
    public ResponseDto(boolean ok) {
        this.ok = ok;
    }
}