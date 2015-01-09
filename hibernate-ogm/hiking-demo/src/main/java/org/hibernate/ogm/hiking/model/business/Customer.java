package org.hibernate.ogm.hiking.model.business;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

/**
 * @author Emmanuel Bernard &lt;emmanuel@hibernate.org&gt;
 */
@Embeddable
public class Customer {
	public String name;
	public String email;
}
