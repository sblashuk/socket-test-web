package com.sockettestweb.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.sockettestweb.parser.model.ClientDetails;
import com.sockettestweb.parser.service.ParserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
@ConditionalOnProperty(value = "STARTUP_CONFIG")
public class ParserConfig {

    @Value("${STARTUP_CONFIG}")
    private String startupConfig;

    @Bean
    public ObjectMapper objectMapperYAML() {
        return new ObjectMapper(new YAMLFactory());
    }

    @Bean
    public JsonNode parseNodes() throws IOException {
        return objectMapperYAML().readTree(new File(startupConfig));
    }

    @Bean
    public ParserService parserService() {
        return new ParserService(objectMapperYAML());
    }

    @Bean
    public List<ClientDetails> extractClientDetailsList() throws IOException {
        return parserService().extractClientDetailsList(parseNodes());
    }
}
