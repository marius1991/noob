package de.noob.noobservice;

public class NoSessionException extends NoobException {

	private static final long serialVersionUID = -1404014776323780300L;
	private static final int CODE = 10;

	public NoSessionException(String message) {
		super(CODE, message);
	}
}
