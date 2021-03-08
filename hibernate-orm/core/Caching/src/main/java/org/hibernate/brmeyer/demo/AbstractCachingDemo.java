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
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.brmeyer.demo.entity.Project;
import org.hibernate.brmeyer.demo.entity.Skill;
import org.hibernate.brmeyer.demo.entity.User;
import org.hibernate.cfg.Configuration;

/**
 * The Class AbstractCachingDemo.
 *
 * @author Brett Meyer
 */
public class AbstractCachingDemo {
	
	/** The session factory. */
	protected final SessionFactory sessionFactory;
	
	/**
	 * Instantiates a new abstract caching demo.
	 */
	protected AbstractCachingDemo() {
		final Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Project.class );
		configuration.addAnnotatedClass( User.class );
		configuration.addAnnotatedClass( Skill.class );
		sessionFactory = configuration.buildSessionFactory(
				new StandardServiceRegistryBuilder().build() );
	}
	
	/**
	 * Open session.
	 *
	 * @return the session
	 */
	protected Session openSession() {
		return sessionFactory.openSession();
	}
	
	/**
	 * Persist data.
	 *
	 * @return the long
	 */
	public long persistData() {
		final Project project = new Project();
		project.setName( "Foo Project" );
		final User user = new User();
		user.setName( "Brett Meyer" );
		final Skill skill = new Skill();
		skill.setName( "Hibernate ORM" );
		user.getSkills().add( skill );
		user.getProjects().add( project );
		project.setAssignee( user );
		
		final Session s = openSession();
		s.getTransaction().begin();
		s.persist(skill);
		s.persist(user);
		s.persist(project);
		s.getTransaction().commit();
		s.close();
		
		return project.getId();
	}
}
