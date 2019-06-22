package org.nill.vorlagen.vorlagen;

public class RuntimeVorlagenException extends RuntimeException
{

	private static final long serialVersionUID = 1L;

	public RuntimeVorlagenException() {
		super();
	}

	public RuntimeVorlagenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RuntimeVorlagenException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeVorlagenException(String message) {
		super(message);
	}

	public RuntimeVorlagenException(Throwable cause) {
		super(cause);
	}

}
