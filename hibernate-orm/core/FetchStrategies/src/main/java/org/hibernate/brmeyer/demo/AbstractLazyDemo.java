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
import org.hibernate.brmeyer.demo.entity.lazy.Comment;
import org.hibernate.brmeyer.demo.entity.lazy.Community;
import org.hibernate.brmeyer.demo.entity.lazy.Donation;
import org.hibernate.brmeyer.demo.entity.lazy.Project;
import org.hibernate.brmeyer.demo.entity.lazy.ServiceEvent;
import org.hibernate.brmeyer.demo.entity.lazy.Skill;
import org.hibernate.brmeyer.demo.entity.lazy.Tool;
import org.hibernate.brmeyer.demo.entity.lazy.User;

/**
 * The Class AbstractLazyDemo.
 *
 * @author Brett Meyer
 */
public abstract class AbstractLazyDemo extends AbstractDemo {
	
	/**
	 * Instantiates a new abstract lazy demo.
	 */
	protected AbstractLazyDemo() {
		super( Comment.class, Community.class, Donation.class, Skill.class, Tool.class,
				Project.class, ServiceEvent.class, User.class);
	}
	
	/**
	 * Persist data.
	 *
	 * @return the int
	 */
	protected int persistData() {
		final User user1 = new User();
		final User user2 = new User();
		final User user3 = new User();
		
		final Skill skill = new Skill();
		user2.getSkills().add( skill );
		user3.getSkills().add( skill );
		
		final Tool tool = new Tool();
		user2.getTools().add( tool );
		user3.getTools().add( tool );
		
		final Community community = new Community();
		community.setCreator( user2 );
		user2.getCommunityMemberships().add( community );
		user3.getCommunityMemberships().add( community );
		
		final ServiceEvent serviceEvent = new ServiceEvent();
		serviceEvent.setOrganizer( user2 );
		user2.getServiceEventsOrganized().add( serviceEvent );
		
		final Project project = new Project();
		project.setSubmitter( user1 );
		project.setOrganizer( user2 );
		project.getVolunteers().add( user3 );
		user1.getProjectsSubmitted().add( project );
		user2.getProjectsOrganized().add( project );
		user3.getProjectsVolunteered().add( project );
		
		final Comment comment = new Comment();
		comment.setProject( project );
		comment.setSubmitter( user3 );
		user3.getComments().add( comment );
		comment.setProject( project );
		project.getComments().add( comment );
		
		final Donation donation = new Donation();
		donation.setUser( user3 );
		user3.getDonations().add( donation );
		donation.setProject( project );
		project.getDonations().add( donation );
		
		final Session session = openSession();
		session.getTransaction().begin();
		session.persist( user1 );
		session.persist( user2 );
		session.persist( user3 );
		session.persist( skill );
		session.persist( tool );
		session.persist( community );
		session.persist( serviceEvent );
		session.persist( project );
		session.persist( comment );
		session.persist( donation );
		session.getTransaction().commit();
		session.close();
		
		return user3.getId();
	}
}
