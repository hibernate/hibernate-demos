/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.demos.ogm101.part3.rest.mapper;

import java.util.List;

import org.hibernate.ogm.demos.ogm101.part3.model.Hike;
import org.hibernate.ogm.demos.ogm101.part3.model.Person;
import org.hibernate.ogm.demos.ogm101.part3.rest.model.HikeDocument;
import org.hibernate.ogm.demos.ogm101.part3.rest.model.PersonDocument;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * A MapStruct-generated mapper for hikes and persons.
 * <p>
 * Contains the methods fo rthe conversion between {@link PersonDocument} and {@link Person} or {@link Hike} and
 * {@link HikeDocument}. It also contains the methods to update a {@link Person} or {@link Hike} using the values in a
 * {@link PersonDocument} or {@link HikeDocument}.
 *
 * @author Gunnar Morling
 */
@Mapper(uses = UriMapper.class, componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourceMapper {

	PersonDocument toPersonDocument(Person person);

	@Mapping(target = "organizedHikes", ignore = true)
	Person toPerson(PersonDocument personDocument);

	@InheritConfiguration(name="toPerson")
	void updatePerson(PersonDocument personDocument, @MappingTarget Person person);

	List<PersonDocument> toPersonDocuments(Iterable<Person> persons);

	@Mapping(target = "date", dateFormat = "yyyy-MM-dd")
	HikeDocument toHikeDocument(Hike hike);

	@InheritInverseConfiguration(name="toHikeDocument")
	Hike toHike(HikeDocument hikeDocument);

	@InheritConfiguration(name="toHike")
	void updateHike(HikeDocument hikeDocument, @MappingTarget Hike hike);

	List<HikeDocument> toHikeDocuments(Iterable<Hike> hikes);
}
