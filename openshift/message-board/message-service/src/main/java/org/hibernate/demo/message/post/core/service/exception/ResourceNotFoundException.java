package org.hibernate.demo.message.post.core.service.exception;

public class ResourceNotFoundException extends Exception {

	public ResourceNotFoundException(String resourceType, Long id) {
		super( resourceType + "[" + id + "] not found!");
	}

}
