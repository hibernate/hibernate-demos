package org.hibernate.brmeyer.demo;

import java.io.Serializable;
import java.text.DateFormat;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionType;

@Entity
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevisionEntity extends DefaultTrackingModifiedEntitiesRevisionEntity implements EntityTrackingRevisionListener {
	private String modifiedBy;
	
	private String ipAddress;
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "CustomRevisionEntity(id = " + getId()
				+ ", revisionDate = " + DateFormat.getDateTimeInstance().format( getRevisionDate() )
				+ ", modifiedEntityNames = " + getModifiedEntityNames()
				+ ", modifiedBy = " + modifiedBy
				+ ", ipAddress = " + ipAddress + ")";
	}

	@Override
	public void newRevision(Object revisionEntity) {
		// do nothing -- here purely to demonstrate capabilities
	}

	@Override
	public void entityChanged(Class entityClass, String entityName, Serializable entityId, RevisionType revisionType,
			Object revisionEntity) {
		// do nothing -- here purely to demonstrate capabilities
	}
}
