package com.sockettestweb.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDetailsDto {
    private String host;
    private Integer port;
    private Boolean reconnect;
}
