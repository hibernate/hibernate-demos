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

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.brmeyer.demo.entity.Project;


/**
 * The Class QueryCachingDemo.
 *
 * @author Brett Meyer
 */
public class QueryCachingDemo extends AbstractCachingDemo {
	
	/**
	 * Gets the project.
	 *
	 * @param id the id
	 * @return the project
	 */
	public Project getProject(long id) {
		final Session s = openSession();
		s.getTransaction().begin();
		final Query q = s.createQuery( "FROM Project WHERE id = :id" );
		q.setParameter( "id", id );
		q.setCacheable( true );
		final Project project = (Project) q.uniqueResult();
		s.getTransaction().commit();
		return project;
	}
	
	/**
	 * Gets the projects.
	 *
	 * @return the projects
	 */
	public List<Project> getProjects() {
		final Session s = openSession();
		s.getTransaction().begin();
		final Query q = s.createQuery( "FROM Project" );
		q.setCacheable( true );
		final List<Project> projects = q.list();
		s.getTransaction().commit();
		return projects;
	}
	
	/**
	 * Update project.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public void updateProject(long id, String name) {
		final Session s = openSession();
		s.getTransaction().begin();
		final Project project = (Project) s.get( Project.class, id );
		project.setName( name );
		s.update( project );
		s.getTransaction().commit();
	}
	
	/**
	 * Evict.
	 */
	public void evict() {
		sessionFactory.getCache().evictDefaultQueryRegion();
	}
	
	/**
	 * Prints the stats.
	 */
	public void printStats() {
		System.out.println("query cache put count: " + sessionFactory.getStatistics().getQueryCachePutCount());
		System.out.println("query cache hit count: " + sessionFactory.getStatistics().getQueryCacheHitCount());
		System.out.println("query cache miss count: " + sessionFactory.getStatistics().getQueryCacheMissCount());
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final QueryCachingDemo demo = new QueryCachingDemo();
		final long projectId = demo.persistData();
		
		System.out.println("GET PROJECT, ATTEMPT #1");
		// DB hit
		Project project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET PROJECT, ATTEMPT #2");
		// query cache hit
		project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET PROJECTS, ATTEMPT #1");
		// DB hit
		List<Project> projects = demo.getProjects();
		demo.printStats();
		
		System.out.println("GET PROJECTS, ATTEMPT #2");
		// query cache hit
		projects = demo.getProjects();
		demo.printStats();
		
		System.out.println("EVICT");
		demo.evict();
		
		System.out.println("GET PROJECT, ATTEMPT #3");
		// DB hit
		project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET PROJECT, ATTEMPT #4");
		// query cache hit
		project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET PROJECTS, ATTEMPT #3");
		// DB hit
		projects = demo.getProjects();
		demo.printStats();
		
		System.out.println("GET PROJECTS, ATTEMPT #4");
		// query cache hit
		projects = demo.getProjects();
		demo.printStats();
		
		System.out.println("UPDATE PROJECT");
		// Automatically evicts Project-related queries in the cache!
		demo.updateProject( project.getId(), "Updated" );
		
		System.out.println("GET PROJECT, ATTEMPT #5");
		// DB hit
		project = demo.getProject(projectId);
		demo.printStats();
		
		System.out.println("GET PROJECTS, ATTEMPT #5");
		// DB hit
		projects = demo.getProjects();
		demo.printStats();
		
		System.exit(0);
	}
}
