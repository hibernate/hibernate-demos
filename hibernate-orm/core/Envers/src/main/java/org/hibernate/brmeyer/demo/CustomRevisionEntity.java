package org.hibernate.brmeyer.demo;

import java.io.Serializable;
import java.text.DateFormat;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionType;


/**
 * The Class CustomRevisionEntity.
 */
@Entity
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity extends DefaultTrackingModifiedEntitiesRevisionEntity implements EntityTrackingRevisionListener {
	
	/** The modified by. */
	private String modifiedBy;
	
	/** The ip address. */
	private String ipAddress;
	
	/**
	 * Gets the modified by.
	 *
	 * @return the modified by
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	/**
	 * Sets the modified by.
	 *
	 * @param modifiedBy the new modified by
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	/**
	 * Gets the ip address.
	 *
	 * @return the ip address
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	/**
	 * Sets the ip address.
	 *
	 * @param ipAddress the new ip address
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity#toString()
	 */
	@Override
	public String toString() {
		return "CustomRevisionEntity(id = " + getId()
				+ ", revisionDate = " + DateFormat.getDateTimeInstance().format( getRevisionDate() )
				+ ", modifiedEntityNames = " + getModifiedEntityNames()
				+ ", modifiedBy = " + modifiedBy
				+ ", ipAddress = " + ipAddress + ")";
	}

	/* (non-Javadoc)
	 * @see org.hibernate.envers.RevisionListener#newRevision(java.lang.Object)
	 */
	@Override
	public void newRevision(Object revisionEntity) {
		// do nothing -- here purely to demonstrate capabilities
	}

	/* (non-Javadoc)
	 * @see org.hibernate.envers.EntityTrackingRevisionListener#entityChanged(java.lang.Class, java.lang.String, java.io.Serializable, org.hibernate.envers.RevisionType, java.lang.Object)
	 */
	@Override
	public void entityChanged(Class entityClass, String entityName, Serializable entityId, RevisionType revisionType,
			Object revisionEntity) {
		// do nothing -- here purely to demonstrate capabilities
	}
}
