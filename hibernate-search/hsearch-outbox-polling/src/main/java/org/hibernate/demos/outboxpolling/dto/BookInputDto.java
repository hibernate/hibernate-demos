package org.hibernate.demos.outboxpolling.dto;

import java.util.List;

public record BookInputDto(String title, int pageCount, List<String> genres,
						   List<Long> authors) {
}
