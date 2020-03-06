package com.sockettestweb.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ClientDto {
    private String name;
    private String host;
    private Integer port;
    private UUID guid;
}

