package com.sockettestweb.service;

import com.sockettestweb.dto.ClientDto;
import com.sockettestweb.mapper.ClientMapper;
import com.sockettestweb.parser.model.ClientDetails;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ClientService {

    @Getter
    private List<ClientDto> clients;

    public ClientService(final ClientMapper clientMapper, @Autowired(required = false) final List<ClientDetails> clientsDetails) {
        this.clients = isNull(clientsDetails) ? new LinkedList<>() : extractClients(clientMapper, clientsDetails);
    }

    private List<ClientDto> extractClients(final ClientMapper clientMapper, final List<ClientDetails> clientsDetails) {
        return clientsDetails.stream()
                .map(clientMapper::toClientDto)
                .peek(clientDto -> clientDto.setGuid(UUID.randomUUID()))
                .collect(Collectors.toList());
    }
}
