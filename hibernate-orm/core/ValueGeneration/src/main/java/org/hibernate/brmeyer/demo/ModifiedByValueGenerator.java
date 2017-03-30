/* 
 * Hibernate, Relational Persistence for Idiomatic Java
 * 
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.brmeyer.demo;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;


/**
 * The Class ModifiedByValueGenerator.
 *
 * @author Brett Meyer
 */
public class ModifiedByValueGenerator implements ValueGenerator<String> {

	/* (non-Javadoc)
	 * @see org.hibernate.tuple.ValueGenerator#generateValue(org.hibernate.Session, java.lang.Object)
	 */
	@Override
	public String generateValue(Session session, Object entity) {
		// Would normally hook into a service to get the current user, etc.
		// Including UUID to show generation ALWAYS happening.
		return "Brett" + " " + UUID.randomUUID().toString();
	}
}
