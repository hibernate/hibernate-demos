package org.hibernate.ogm.hiking.model.business;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
@Embeddable
public class Customer {
	public String name;

	@Email
	public String email;
}
