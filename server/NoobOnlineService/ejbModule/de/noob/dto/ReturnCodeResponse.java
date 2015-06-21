package de.noob.dto;

import java.io.Serializable;

/**
 * Diese Klasse ist die Grundlage für alle anderen Response- und TO-Klassen, damit diese immer einen ReturnCode
 * und eine Message enthalten, wenn sie zum Client gesendet werden. Ein ReturnCode von 0 bedeutet,
 * dass eine Methode der de.noob.noobservice.NoobOnlineServiceBean korrekt ausgeführt wurde, ein Code von 1-99 bedeutet, dass ein Fehler aufgetreten ist.
 * Die enthaltene Message beschreibt das Problem kurz, und kann zum Beispiel auch dem User im Client ausgegeben werden.
 * @author Philipp Ringele
 *
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
