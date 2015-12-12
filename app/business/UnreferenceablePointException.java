package business;

import models.ReferenceablePoint;

/**
 * @author mdelapenya
 */
public class UnreferenceablePointException extends Exception{

	public UnreferenceablePointException(ReferenceablePoint point) {
		this("Cannot make a reference to the point " + point);
	}

	public UnreferenceablePointException(String message) {
		super(message);
	}

	public UnreferenceablePointException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnreferenceablePointException(Throwable cause) {
		super(cause);
	}

}
