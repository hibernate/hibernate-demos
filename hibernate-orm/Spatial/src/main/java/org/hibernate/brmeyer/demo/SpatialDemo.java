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

import geodb.GeoDB;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionImplementor;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @author Brett Meyer
 */
public class SpatialDemo {
	
	private final SessionFactory sessionFactory;
	
	public SpatialDemo() {
		final Configuration configuration = new Configuration();
		configuration.addAnnotatedClass( Project.class );
		sessionFactory = configuration.buildSessionFactory(
				new StandardServiceRegistryBuilder().build() );
	}
	
	public void addProject(String name, String locationWkt) throws Exception {
		final Project project = new Project();
		project.setName( name );
		
		project.setLocation( (Point) wktToGeometry( locationWkt ));
		
		final Session s = openSession();
		s.getTransaction().begin();
		s.persist( project );
		s.getTransaction().commit();
		s.close();
	}

	@SuppressWarnings("unchecked")
	public List<Project> getProjects(String wktFilter) throws Exception {
		final Session s = openSession();
		s.getTransaction().begin();

		final Geometry filter = wktToGeometry(wktFilter);
	    final Query query = s.createQuery("FROM Project p WHERE WITHIN(p.location, :filter) = TRUE");
	    query.setParameter("filter", filter);
	    final List<Project> projects = query.list();
	    
	    s.getTransaction().commit();
	    s.close();
	    
	    return projects;
	}
	
	private Geometry wktToGeometry(String wktString) throws Exception {
		final WKTReader wktReader = new WKTReader();
		return wktReader.read( wktString );
	}
	
	private Session openSession() throws Exception {
		final Session s = sessionFactory.openSession();
		final SessionImplementor sImpl = (SessionImplementor) s;
		// init GeoDB H2 extension
		GeoDB.InitGeoDB( sImpl.connection() );
		return s;
	}

	public static void main(String[] args) {
		try {
			final SpatialDemo demo = new SpatialDemo();
			demo.addProject("foo", "POINT(1 2)");
			
			printProjects(demo.getProjects("POLYGON((0 0, 4 0, 4 4, 0 4, 0 0))"));
			printProjects(demo.getProjects("POLYGON((10 10, 14 10, 14 14, 10 14, 10 10))"));

			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void printProjects(List<Project> projects) {
		System.out.println("FOUND PROJECTS: " + projects.size());
		for (Project project : projects) {
			System.out.println(project.getId() + " " + project.getName());
		}
	}
}
