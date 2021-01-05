package org.hibernate.demos.hsearchfeatureexamples.dto.mapper;

import java.util.List;

import org.hibernate.demos.hsearchfeatureexamples.dto.FashionCollectionInputDto;
import org.hibernate.demos.hsearchfeatureexamples.dto.FashionCollectionOutputDto;
import org.hibernate.demos.hsearchfeatureexamples.model.FashionCollection;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(uses = { LongIdMapper.class })
public interface FashionCollectionMapper {

	void input(@MappingTarget FashionCollection target, FashionCollectionInputDto source);

	FashionCollectionOutputDto output(FashionCollection source);

	List<FashionCollectionOutputDto> output(List<FashionCollection> source);

}
