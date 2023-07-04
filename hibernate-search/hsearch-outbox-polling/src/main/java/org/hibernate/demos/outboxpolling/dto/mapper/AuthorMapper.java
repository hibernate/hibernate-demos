package org.hibernate.demos.outboxpolling.dto.mapper;

import org.hibernate.demos.outboxpolling.dto.AuthorInputDto;
import org.hibernate.demos.outboxpolling.dto.AuthorOutputDto;
import org.hibernate.demos.outboxpolling.model.Author;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class AuthorMapper
		extends InputOutputMapper<Author, AuthorInputDto, AuthorOutputDto> {

	@Mapping(target = "books", ignore = true)
	@Override
	public abstract void input(@MappingTarget Author target, AuthorInputDto source);

}
