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

import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.brmeyer.demo.AbstractLazyDemo;
import org.hibernate.brmeyer.demo.entity.lazy.User;


/**
 * The Class Select.
 *
 * @author Brett Meyer
 */
public class Select extends AbstractLazyDemo {

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
				.setFetchMode( "communityMemberships", FetchMode.SELECT ) // not necessary (default)
				.list();
		
		// init
		users.get( 0 ).getCommunityMemberships().size();
		users.get( 1 ).getCommunityMemberships().size();

		session.getTransaction().commit();
		return users;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final Select demo = new Select();
		demo.persistData();
		final List<User> users = demo.getUsers();
		// ensure it was fetched
		for ( final User user : users ) {
			System.out.println( "fetched: " + Hibernate.isInitialized( user.getCommunityMemberships() ) );
		}
		
		System.exit(0);
	}
}
