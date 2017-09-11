package org.hibernate.ogm.demo.playsport.rest.exception;

public class UispNotFoundException extends Exception {

    public UispNotFoundException(Class subject, String param, String value) {

        super(subject.getSimpleName() + " Not Found with " + param + " = " + value);

    }
}
