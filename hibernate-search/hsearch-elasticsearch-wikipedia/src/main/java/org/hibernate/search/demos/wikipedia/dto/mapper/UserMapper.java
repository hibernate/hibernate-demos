package org.hibernate.search.demos.wikipedia.dto.mapper;

import org.hibernate.search.demos.wikipedia.data.User;
import org.hibernate.search.demos.wikipedia.dto.UserOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

	UserOutputDto output(User user);

}
