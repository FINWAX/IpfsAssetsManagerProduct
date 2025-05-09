package com.example.ipfsmaven.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NodeDto {
    @NotBlank
    private String name;
    @NotBlank
    private String multiaddr;
}
