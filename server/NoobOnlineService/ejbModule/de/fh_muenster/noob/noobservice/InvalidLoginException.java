package de.fh_muenster.noob.noobservice;

public class InvalidLoginException extends NoobException {

	private static final long serialVersionUID = -6167328276700429053L;
	private static final int CODE = 20;
	
	public InvalidLoginException(String message) {
		super(CODE, message);
	}

}
