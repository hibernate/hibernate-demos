package com.example.ogm.data.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.example.ogm.core.user.Celebrity;

@Entity
public class CelebrityJpaEntity {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;

	public CelebrityJpaEntity() {
	}

	public CelebrityJpaEntity(final String name) {
		this.name = name;
	}

	public CelebrityJpaEntity(final Celebrity celebrity) {
		this.id = celebrity.getId();
		this.name = celebrity.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Celebrity toCelebrity() {
		Celebrity user = new Celebrity( name );
		user.setId( id );
		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null ) {
			return false;
		}
		if ( getClass() != obj.getClass() ) {
			return false;
		}
		CelebrityJpaEntity other = (CelebrityJpaEntity) obj;
		if ( name == null ) {
			if ( other.name != null ) {
				return false;
			}
		}
		else if ( !name.equals( other.name ) ) {
			return false;
		}
		return true;
	}
}
