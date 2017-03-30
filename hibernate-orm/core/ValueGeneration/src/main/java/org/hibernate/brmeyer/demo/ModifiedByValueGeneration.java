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

import org.hibernate.tuple.AnnotationValueGeneration;
import org.hibernate.tuple.GenerationTiming;
import org.hibernate.tuple.ValueGenerator;


/**
 * The Class ModifiedByValueGeneration.
 *
 * @author Brett Meyer
 */
public class ModifiedByValueGeneration implements AnnotationValueGeneration<ModifiedBy> {
	
	/** The generator. */
	private final ModifiedByValueGenerator generator = new ModifiedByValueGenerator();

	/* (non-Javadoc)
	 * @see org.hibernate.tuple.AnnotationValueGeneration#initialize(java.lang.annotation.Annotation, java.lang.Class)
	 */
	@Override
	public void initialize(ModifiedBy annotation, Class<?> propertyType) {
	}

	/* (non-Javadoc)
	 * @see org.hibernate.tuple.ValueGeneration#getGenerationTiming()
	 */
	public GenerationTiming getGenerationTiming() {
		return GenerationTiming.ALWAYS;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.tuple.ValueGeneration#getValueGenerator()
	 */
	public ValueGenerator<?> getValueGenerator() {
		return generator;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.tuple.ValueGeneration#referenceColumnInSql()
	 */
	public boolean referenceColumnInSql() {
		// not used -- needed only for in-DB generation
		return false;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.tuple.ValueGeneration#getDatabaseGeneratedReferencedColumnValue()
	 */
	public String getDatabaseGeneratedReferencedColumnValue() {
		// not used -- needed only for in-DB generation
		return null;
	}
}
