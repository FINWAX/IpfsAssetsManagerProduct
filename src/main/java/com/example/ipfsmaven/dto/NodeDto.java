package com.example.ipfsmaven.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NodeDto {
    @NotBlank
    private String name;
    @Pattern(regexp = "^/ip4/((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?).){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)/tcp/\\d{1,5}$",
            message = "Не соответствует формату IP адреса")
    private String multiaddr;
}
