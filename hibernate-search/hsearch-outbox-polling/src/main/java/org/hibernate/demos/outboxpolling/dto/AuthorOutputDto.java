package org.hibernate.demos.outboxpolling.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "firstName", "lastName" })
public record AuthorOutputDto(long id, String firstName, String lastName) {
}
