package de.fh_muenster.noobservice;

public class NoobException extends Exception {

	private static final long serialVersionUID = 6942631978782468780L;
	
	private int errorCode;

	public NoobException(int errorCode, String message) {
		super(message);
		this.setErrorCode(errorCode);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
