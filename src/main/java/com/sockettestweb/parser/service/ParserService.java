package com.sockettestweb.parser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sockettestweb.parser.model.ClientDetails;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;
import static java.util.Spliterators.spliteratorUnknownSize;

@AllArgsConstructor
public class ParserService {

    private static final String CLIENTS_DEFINITION_SECTION = "clients";

    private ObjectMapper mapper;

    public List<ClientDetails> extractClientDetailsList(final JsonNode clientsNode) throws IOException {
        return StreamSupport.stream(spliteratorUnknownSize(clientsNode.get(CLIENTS_DEFINITION_SECTION).fields(), Spliterator.ORDERED), true)
                .map(this::parseClientDetails)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ClientDetails> parseClientDetails(final Map.Entry<String, JsonNode> entry) {
        try {
            ClientDetails clientDetails = mapper.treeToValue(entry.getValue(), ClientDetails.class);
            String clientName = clientDetails.getName();
            clientDetails.setName((isNull(clientName) || clientName.isEmpty()) ? entry.getKey() : clientName);
            return Optional.of(clientDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
