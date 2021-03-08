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
package org.hibernate.brmeyer.demo.fetchstyle;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.brmeyer.demo.AbstractLazyDemo;
import org.hibernate.brmeyer.demo.entity.lazy.Community;
import org.hibernate.brmeyer.demo.entity.lazy.User;


/**
 * The Class Batch.
 *
 * @author Brett Meyer
 */
public class Batch extends AbstractLazyDemo {
	
	/* (non-Javadoc)
	 * @see org.hibernate.brmeyer.demo.AbstractLazyDemo#persistData()
	 */
	@Override
	protected int persistData() {
		// More data is needed to demo batching, in addition to super#persistData()
		final Session session = openSession();
		session.getTransaction().begin();
		for (int i = 0; i < 10; i++) {
			final User user = new User();
			final Community community = new Community();
			user.getCommunitiesCreated().add( community );
			session.persist( community );
			session.persist( user );
		}
		session.getTransaction().commit();
		session.close();
		
		return super.persistData();
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		final Session session = openSession();
		session.getTransaction().begin();

		final List<User> users = session
				.createCriteria( User.class )
				.list();
		
		// init all (#communitiesCreated uses batching)
		for (final User user : users) {
			user.getCommunitiesCreated().size();
		}

		session.getTransaction().commit();
		return users;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final Batch demo = new Batch();
		demo.persistData();
		final List<User> users = demo.getUsers();
		// ensure it was fetched
		for ( final User user : users ) {
			System.out.println( "fetched: " + Hibernate.isInitialized( user.getCommunitiesCreated() ) );
		}
		
		System.exit(0);
	}
}
