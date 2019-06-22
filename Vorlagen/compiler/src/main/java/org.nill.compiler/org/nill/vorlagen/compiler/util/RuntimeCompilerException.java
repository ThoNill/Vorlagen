package org.nill.vorlagen.compiler.util;

public class RuntimeCompilerException extends RuntimeException
{

	public RuntimeCompilerException() {
		super();
	}

	public RuntimeCompilerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RuntimeCompilerException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeCompilerException(String message) {
		super(message);
	}

	public RuntimeCompilerException(Throwable cause) {
		super(cause);
	}

}
