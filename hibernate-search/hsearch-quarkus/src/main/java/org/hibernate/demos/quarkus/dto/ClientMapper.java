package org.hibernate.demos.quarkus.dto;

import org.hibernate.demos.quarkus.domain.BusinessManager;
import org.hibernate.demos.quarkus.domain.Client;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface ClientMapper {

	ClientRetrieveDto toDto(Client client);

	void fromDto(@MappingTarget Client client, ClientCreateUpdateDto dto);

	BusinessManagerRetrieveDto toDto(BusinessManager businessManager);

	void fromDto(@MappingTarget BusinessManager businessManager, BusinessManagerCreateUpdateDto dto);

}
