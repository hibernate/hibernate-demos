package org.hibernate.ogm.demo.playsport.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class Athlete extends Person {

	public static final String CODE_REGEX = "\\d{9}";

	@Column(unique = true, nullable = false)
	@Pattern(regexp = "\\d{9}")
	@Field(analyze = Analyze.NO)
	private String uispCode;

	@ManyToOne
	private Club club;

	public String getUispCode() {
		return uispCode;
	}

	public void setUispCode(String uispCode) {
		this.uispCode = uispCode;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		if ( !super.equals( o ) ) {
			return false;
		}

		Athlete athlete = (Athlete) o;

		return uispCode != null ? uispCode.equals( athlete.uispCode ) : athlete.uispCode == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + ( uispCode != null ? uispCode.hashCode() : 0 );
		return result;
	}

}
