package com.sockettestweb.mapper;

import com.sockettestweb.dto.ClientDto;
import com.sockettestweb.parser.model.ClientDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "guid", ignore = true)
    ClientDto toClientDto(ClientDetails clientDetails);
}