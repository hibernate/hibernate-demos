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
import org.hibernate.brmeyer.demo.entity.Project;
import org.hibernate.brmeyer.demo.entity.User;


/**
 * The Class SecondLevelCachingDemo.
 *
 * @author Brett Meyer
 */
public class SecondLevelCachingDemo extends AbstractCachingDemo {
	
	/**
	 * Gets the project.
	 *
	 * @param id the id
	 * @return the project
	 */
	public Project getProject(long id) {
		final Session s = openSession();
		s.getTransaction().begin();
		final Project project = (Project) s.get( Project.class, id );
		s.getTransaction().commit();
		return project;
	}
	
	/**
	 * Gets the user.
	 *
	 * @param id the id
	 * @return the user
	 */
	public User getUser(long id) {
		final Session s = openSession();
		s.getTransaction().begin();
		final User user = (User) s.get( User.class, id );
		s.getTransaction().commit();
		return user;
	}
	
	/**
	 * Evict project.
	 *
	 * @param projectId the project id
	 */
	public void evictProject(long projectId) {
		sessionFactory.getCache().evictEntity( Project.class, projectId );
	}
	
	/**
	 * Prints the stats.
	 */
	public void printStats() {
		System.out.println("2lc put count: " + sessionFactory.getStatistics().getSecondLevelCachePutCount());
		System.out.println("2lc hit count: " + sessionFactory.getStatistics().getSecondLevelCacheHitCount());
		System.out.println("2lc miss count: " + sessionFactory.getStatistics().getSecondLevelCacheMissCount());
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final SecondLevelCachingDemo demo = new SecondLevelCachingDemo();
		final long projectId = demo.persistData();
		
		System.out.println("GET PROJECT, ATTEMPT #1");
		// DB hit
		Project project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET PROJECT, ATTEMPT #2");
		// 2LC hit
		project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET USER");
		// Will NOT cause another fetch!  The Project put into the 2LC also puts the fetched associations.
		demo.getUser(project.getAssignee().getId());
		demo.printStats();
		
		System.out.println("EVICT");
		demo.evictProject( projectId );
		
		System.out.println("GET USER");
		// Even though the Project was evicted, will NOT cause another fetch unless the User (or all entities)
		// is explicitly evicted.
		demo.getUser(project.getAssignee().getId());
		demo.printStats();
		
		System.out.println("GET PROJECT, ATTEMPT #3");
		// DB hit
		project = demo.getProject(projectId);
		demo.printStats();
		
		System.exit(0);
	}
}
