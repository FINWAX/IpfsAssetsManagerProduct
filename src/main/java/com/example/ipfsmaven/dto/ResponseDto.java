package com.example.ipfsmaven.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private boolean ok;
    private String error;

    public ResponseDto(boolean ok, String error) {
        this.ok = ok;
        this.error = error;
    }
    public ResponseDto(boolean ok) {
        this.ok = ok;
    }
}