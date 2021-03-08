package org.hibernate.brmeyer.demo;

import org.hibernate.envers.RevisionListener;



/**
 * The listener interface for receiving customRevision events.
 * The class that is interested in processing a customRevision
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addCustomRevisionListener<code> method. When
 * the customRevision event occurs, that object's appropriate
 * method is invoked.
 *
 * @see CustomRevisionEvent
 */
public class CustomRevisionListener implements RevisionListener {

	/* (non-Javadoc)
	 * @see org.hibernate.envers.RevisionListener#newRevision(java.lang.Object)
	 */
	@Override
	public void newRevision(Object revisionEntity) {
		CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;
		// ex: authenticated user, LDAP, etc.
		customRevisionEntity.setModifiedBy( "brmeyer" );
		customRevisionEntity.setIpAddress( "127.0.0.1" );
	}

}
