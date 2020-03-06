package com.sockettestweb.parser.model;

import lombok.Data;

@Data
public class ClientDetails {
    private String name;
    private String host;
    private Integer port;
    private Boolean retry;
}
