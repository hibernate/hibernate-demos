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

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;


/**
 * The Class Project.
 *
 * @author Brett Meyer
 */
@Entity
public class Project {
	
	/** The id. */
	@Id
	@GeneratedValue
	private long id;
	
	/** The created. */
	@CreationTimestamp
	private Calendar created;
	
	/** The last updated. */
	@UpdateTimestamp
	private Calendar lastUpdated;
	
	/** The db generated. */
	@Generated(GenerationTime.INSERT)
	@Column(columnDefinition = "varchar(255) default 'DB_GENERATED'")
	private String dbGenerated;
	
	/** The mem generated. */
	@GeneratorType(type = CustomValueGenerator.class, when = GenerationTime.ALWAYS)
	private String memGenerated;
	
	/** The last modified by. */
	@ModifiedBy
	private String lastModifiedBy;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PROJECT\n"
				+ "created: " + created + "\n"
				+ "lastUpdated: " + lastUpdated + "\n"
				+ "dbGenerated: " + dbGenerated + "\n"
				+ "memGenerated: " + memGenerated + "\n"
				+ "lastModifiedBy: " + lastModifiedBy;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Gets the created.
	 *
	 * @return the created
	 */
	public Calendar getCreated() {
		return created;
	}
	
	/**
	 * Sets the created.
	 *
	 * @param created the new created
	 */
	public void setCreated(Calendar created) {
		this.created = created;
	}
	
	/**
	 * Gets the last updated.
	 *
	 * @return the last updated
	 */
	public Calendar getLastUpdated() {
		return lastUpdated;
	}
	
	/**
	 * Sets the last updated.
	 *
	 * @param lastUpdated the new last updated
	 */
	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	/**
	 * Gets the db generated.
	 *
	 * @return the db generated
	 */
	public String getDbGenerated() {
		return dbGenerated;
	}
	
	/**
	 * Sets the db generated.
	 *
	 * @param dbGenerated the new db generated
	 */
	public void setDbGenerated(String dbGenerated) {
		this.dbGenerated = dbGenerated;
	}
	
	/**
	 * Gets the mem generated.
	 *
	 * @return the mem generated
	 */
	public String getMemGenerated() {
		return memGenerated;
	}
	
	/**
	 * Sets the mem generated.
	 *
	 * @param memGenerated the new mem generated
	 */
	public void setMemGenerated(String memGenerated) {
		this.memGenerated = memGenerated;
	}
	
	/**
	 * Gets the last modified by.
	 *
	 * @return the last modified by
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	/**
	 * Sets the last modified by.
	 *
	 * @param lastModifiedBy the new last modified by
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
}
