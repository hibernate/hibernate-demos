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
 * @author Brett Meyer
 */
@Entity
public class Project {
	@Id
	@GeneratedValue
	private long id;
	
	@CreationTimestamp
	private Calendar created;
	
	@UpdateTimestamp
	private Calendar lastUpdated;
	
	@Generated(GenerationTime.INSERT)
	@Column(columnDefinition = "varchar(255) default 'DB_GENERATED'")
	private String dbGenerated;
	
	@GeneratorType(type = CustomValueGenerator.class, when = GenerationTime.ALWAYS)
	private String memGenerated;
	
	@ModifiedBy
	private String lastModifiedBy;
	
	@Override
	public String toString() {
		return "PROJECT\n"
				+ "created: " + created + "\n"
				+ "lastUpdated: " + lastUpdated + "\n"
				+ "dbGenerated: " + dbGenerated + "\n"
				+ "memGenerated: " + memGenerated + "\n"
				+ "lastModifiedBy: " + lastModifiedBy;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Calendar getCreated() {
		return created;
	}
	
	public void setCreated(Calendar created) {
		this.created = created;
	}
	
	public Calendar getLastUpdated() {
		return lastUpdated;
	}
	
	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public String getDbGenerated() {
		return dbGenerated;
	}
	
	public void setDbGenerated(String dbGenerated) {
		this.dbGenerated = dbGenerated;
	}
	
	public String getMemGenerated() {
		return memGenerated;
	}
	
	public void setMemGenerated(String memGenerated) {
		this.memGenerated = memGenerated;
	}
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
}
