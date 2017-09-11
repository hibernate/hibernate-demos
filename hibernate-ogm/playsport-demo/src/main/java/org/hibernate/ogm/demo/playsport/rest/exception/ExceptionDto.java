package org.hibernate.ogm.demo.playsport.rest.exception;

/**
 *
 * ExceptionDto class wraps the exception info:
 * <ul>
 *     <li>exception class name</li>
 *     <li>exception message</li>
 * </ul>
 *
 * Used to produce JSON / XML error response
 *
 */
public class ExceptionDto {

    private final String exception;
    private final String message;

    public ExceptionDto(Throwable ex) {

        this.exception = ex.getClass().getName();
        this.message = ex.getMessage();

    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

}
