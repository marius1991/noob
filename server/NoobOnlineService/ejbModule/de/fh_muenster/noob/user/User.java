package de.fh_muenster.noob.user;

public interface User {
	public int getId();

	public String getName();

	public void setName(String name);

	public String getPassword();

	public void setPassword(String password);

	public String getEmail();

	public void setEmail(String email);
}
