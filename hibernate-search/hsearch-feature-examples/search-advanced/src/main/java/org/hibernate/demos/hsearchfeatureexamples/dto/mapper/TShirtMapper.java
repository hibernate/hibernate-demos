package org.hibernate.demos.hsearchfeatureexamples.dto.mapper;

import java.util.List;

import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtInputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtOutputDto;
import org.hibernate.demos.hsearchfeatureexamples.model.TShirt;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(uses = { LongIdMapper.class, TShirtVariantMapper.class, FashionCollectionMapper.class })
public interface TShirtMapper {

	void input(@MappingTarget TShirt target, TShirtInputDto source);

	TShirtOutputDto output(TShirt source);

	default List<TShirtOutputDto> output(List<TShirt> source, boolean brief) {
		return brief ? outputBrief( source ) : output( source );
	}

	List<TShirtOutputDto> output(List<TShirt> source);

	@Mapping(target = "variants", ignore = true)
	@Mapping(target = "collection", ignore = true)
	@Named("brief")
	TShirtOutputDto outputBrief(TShirt source);

	@IterableMapping(qualifiedByName = "brief")
	List<TShirtOutputDto> outputBrief(List<TShirt> source);

}
