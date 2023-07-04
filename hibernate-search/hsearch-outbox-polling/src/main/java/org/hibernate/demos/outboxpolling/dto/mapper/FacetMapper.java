package org.hibernate.demos.outboxpolling.dto.mapper;

import java.util.Map;

import org.mapstruct.Mapper;

@Mapper
interface FacetMapper {

	Map<String, Long> output(Map<?, Long> source);

	default String outputMapKey(Object source) {
		return source.toString();
	}

}
