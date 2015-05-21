package de.fh_muenster.noob.noobservice;

public class InvalidDataException extends NoobException {

	private static final long serialVersionUID = -6341493922214496480L;
	private static final int CODE = 30;
	
	public InvalidDataException(String message) {
		super(CODE, message);
	}
}
