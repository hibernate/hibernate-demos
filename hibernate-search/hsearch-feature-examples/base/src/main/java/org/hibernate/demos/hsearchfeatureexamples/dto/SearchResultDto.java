package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;

import lombok.Value;

@Value
@JsonbPropertyOrder({ "totalHitCount", "hits" })
public class SearchResultDto<T> {

	long totalHitCount;
	List<T> hits;

}
