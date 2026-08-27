/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.demos.hswithes.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

@Entity
public class Character {

	@Id
	@GeneratedValue
	public long id;

	@FullTextField
	public String nickName;

	@FullTextField
	public String specialPower;

	@ManyToMany(mappedBy="characters")
	public List<VideoGame> appearsIn = new ArrayList<>();

	Character() {
	}

	public Character(String nickName, String specialPower) {
		this.nickName = nickName;
		this.specialPower = specialPower;
	}

	@Override
	public String toString() {
		return "Character [id=" + id + ", nickName=" + nickName + ", specialPower=" + specialPower + "]";
	}
}
