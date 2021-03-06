package de.fh_muenster.noob;

import java.io.Serializable;

/**
 * Objekte dieser Klasse werden vom Server bei diversen Operationen zur App gesendet
 * Sie enthalten Statusinformationen
 * Alle anderen TOs erben von dieser Klasse
 * @author marius, philipp
 */
public class ReturnCodeResponse implements Serializable {

	private static final long serialVersionUID = 3397348747136795401L;
	private static final int CODE_ERROR = 99;
	
	private int returnCode;
	private String message;
	
	public ReturnCodeResponse() {
		this.returnCode = CODE_ERROR;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
