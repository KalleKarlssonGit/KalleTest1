package se.atg.service.harrykart.exc;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = -8079454849611022224L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(final String message) {
		super(message);
	}

}