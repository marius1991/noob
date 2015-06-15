package de.fh_muenster.noob;

public class UserLoginResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = -3173158310918408228L;

	private int sessionId;
	
	private int userId;
	
	
	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
