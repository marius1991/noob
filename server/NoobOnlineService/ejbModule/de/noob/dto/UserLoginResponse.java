package de.noob.dto;

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
