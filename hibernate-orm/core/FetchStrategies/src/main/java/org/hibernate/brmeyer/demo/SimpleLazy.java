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

import org.hibernate.Session;
import org.hibernate.brmeyer.demo.entity.lazy.User;

/**
 * The Class SimpleLazy.
 *
 * @author Brett Meyer
 */
public class SimpleLazy extends AbstractLazyDemo {
	
	/**
	 * Similar to {@link Eager}, demonstrating a typical, over-simplified, over-scoped DAO method.  However, for
	 * demo-purposes, all collections are LAZY (as they usually should be), resulting in a single select.
	 *
	 * @param id the id
	 * @return the user
	 */
	public boolean getUser(int id) {
		final Session session = openSession();
		session.getTransaction().begin();
		final User user = (User) session.get( User.class, id );
		
		// this will fetch tools
//		user.getTools().size();
		
		// this will extra lazy fetch projects
//		user.getComments().get( 0 );
		
		session.getTransaction().commit();
		
		return user != null;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main (String[] args) {
		final SimpleLazy demo = new SimpleLazy();
		int userId = demo.persistData();
		demo.getUser( userId );
		
		System.exit(0);
	}
}
