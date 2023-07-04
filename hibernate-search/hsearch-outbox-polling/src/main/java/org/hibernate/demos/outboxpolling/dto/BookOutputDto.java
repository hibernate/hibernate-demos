package org.hibernate.demos.outboxpolling.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "title", "pageCount" })
public record BookOutputDto(long id, String title, int pageCount, List<String> genres,
							List<AuthorOutputDto> authors) {

}
