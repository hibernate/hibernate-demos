package org.hibernate.ogm.hiking.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
@Entity
public class Trip {
	@Id
	@GeneratedValue(strategy= GenerationType.TABLE)
	public long id;

	public String name;

	@Temporal(TemporalType.DATE )
	public Date startDate;

	@Temporal(TemporalType.DATE )
	public Date endDate;

	public double price;

	@Embedded
	public Person organizer;

	@OneToMany(mappedBy="recommendedTrip")
	public Set<Hike> availableHikes = new HashSet<Hike>();
}
