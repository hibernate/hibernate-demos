package org.hibernate.ogm.hiking.model.business;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
@Entity
@Table(name="TripOrder")
public class Order {
	@Id
	@GeneratedValue
	public Long id;

	@OrderNumber
	public String number;

	public long tripId;

	@Embedded @Valid
	public Customer customer;
}
