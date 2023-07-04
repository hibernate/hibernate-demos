package org.hibernate.demos.outboxpolling.dto.mapper;

import org.hibernate.demos.outboxpolling.dto.BookInputDto;
import org.hibernate.demos.outboxpolling.dto.BookOutputDto;
import org.hibernate.demos.outboxpolling.model.Book;

import org.mapstruct.Mapper;

@Mapper(uses = { LongIdMapper.class, AuthorMapper.class })
public abstract class BookMapper
		extends InputOutputMapper<Book, BookInputDto, BookOutputDto> {

}
