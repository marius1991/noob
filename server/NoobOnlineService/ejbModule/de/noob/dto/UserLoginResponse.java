package de.noob.dto;

/**
 * Diese Klasse erweitet ReturnCodeResponse, sodass auch eine SessionID mitgeben werden kann.
 * @author Philipp Ringele
 *
 */
public class UserLoginResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -3173158310918408228L;

	private int sessionId;
	
	
	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

}
