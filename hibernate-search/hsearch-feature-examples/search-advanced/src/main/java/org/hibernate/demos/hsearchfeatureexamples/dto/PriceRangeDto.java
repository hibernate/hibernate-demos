package org.hibernate.demos.hsearchfeatureexamples.dto;

import java.math.BigDecimal;

import org.hibernate.search.util.common.data.Range;

public enum PriceRangeDto {

	_0_5( Range.canonical( BigDecimal.ZERO, new BigDecimal( "5" ) ) ),
	_5_10( Range.canonical( new BigDecimal( "5" ), new BigDecimal( "10" ) ) ),
	_10_15( Range.canonical( new BigDecimal( "10" ), new BigDecimal( "15" ) ) ),
	_15_20( Range.canonical( new BigDecimal( "15" ), new BigDecimal( "20" ) ) ),
	_20_PLUS( Range.canonical( new BigDecimal( "20" ), null ) );

	public final Range<BigDecimal> value;

	PriceRangeDto(Range<BigDecimal> value) {
		this.value = value;
	}
}
