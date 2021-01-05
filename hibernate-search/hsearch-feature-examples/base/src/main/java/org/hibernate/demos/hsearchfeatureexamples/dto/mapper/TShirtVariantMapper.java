package org.hibernate.demos.hsearchfeatureexamples.dto.mapper;

import org.hibernate.demos.hsearchfeatureexamples.dto.TShirtVariantDto;
import org.hibernate.demos.hsearchfeatureexamples.model.TShirtVariant;

import org.mapstruct.Mapper;

@Mapper
public interface TShirtVariantMapper {

	TShirtVariant input(TShirtVariantDto source);

	TShirtVariantDto output(TShirtVariant source);

}
