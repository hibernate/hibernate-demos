package org.hibernate.search.demos.wikipedia.dto.mapper;

import org.hibernate.search.demos.wikipedia.data.Page;
import org.hibernate.search.demos.wikipedia.dto.PageInputDto;
import org.hibernate.search.demos.wikipedia.dto.PageOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserMapper.class)
public interface PageMapper {

	PageMapper INSTANCE = Mappers.getMapper( PageMapper.class );

	PageOutputDto output(Page page);
	
	@Mapping(target = "content", ignore = true)
	PageOutputDto outputSummary(Page page);

	void input(@MappingTarget Page page, PageInputDto dto);

}
