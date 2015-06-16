package de.noob.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.noob.noobservice.CityListResponse;
import de.noob.noobservice.NoobOnlineServiceBean;
import de.noob.noobservice.NoobOnlineServiceBeanService;
import de.noob.noobservice.ReturnCodeResponse;
import de.noob.noobservice.UserLoginResponse;
import de.noob.noobservice.UserTO;

public class ServerTest {
	
	private NoobOnlineServiceBean remoteSystem;
	
	@Before
	public void beforeTest() {
		NoobOnlineServiceBeanService service = new NoobOnlineServiceBeanService();
		remoteSystem = service.getNoobOnlineServiceBeanPort();
	}
	
	@Test
	public void registerLoginLogoutDeleteScenario() {
		//================RegisterTests====================================
		//Passwörter falsch
		ReturnCodeResponse registerRe1 = remoteSystem.register("philipp", "p@p.de", "12345678", "123456789");
		assertEquals("ReturnCode muss 1 sein.", 1, registerRe1.getReturnCode());
		//Alles ok.
		ReturnCodeResponse registerRe = remoteSystem.register("philipp", "p@p.de", "12345678", "12345678");
		assertEquals("ReturnCode muss 0 sein.", 0, registerRe.getReturnCode());
		//User schon vorhanden
		ReturnCodeResponse registerRe2 = remoteSystem.register("philipp", "p@p.de", "12345678", "12345678");
		assertEquals("ReturnCode muss 2 sein.", 2, registerRe2.getReturnCode());
		
		//================LoginTests==============================
		//Passwort falsch
		UserLoginResponse loginRe1 = remoteSystem.login("p@p.de", "123456789");
		assertEquals("ReturnCode muss 1 sein.", 1, loginRe1.getReturnCode());
		//Email falsch
		UserLoginResponse loginRe2 = remoteSystem.login("p@pp.de", "12345678");
		assertEquals("ReturnCode muss 2 sein.", 2, loginRe2.getReturnCode());
		//Alles ok
		UserLoginResponse loginRe = remoteSystem.login("p@p.de", "12345678");
		assertEquals("ReturnCode muss 0 sein.", 0, loginRe.getReturnCode());
		
		//================LogoutTests==================================
		//Alles ok
		ReturnCodeResponse logoutRe = remoteSystem.logout(loginRe.getSessionId());
		assertEquals("ReturnCode muss 0 sein.", 0, logoutRe.getReturnCode());
		//Falsche SessionId
		ReturnCodeResponse logoutRe1 = remoteSystem.logout(999999);
		assertEquals("ReturnCode muss 1 sein.", 1, logoutRe1.getReturnCode());
		
		//===============DeleteUserTest===============================
		//Alles Ok
		UserLoginResponse ulre = remoteSystem.login("p@p.de", "12345678");
		assertEquals(ulre.getMessage(), 0, ulre.getReturnCode());
		//Passwort falsch
		ReturnCodeResponse deleteRe2 = remoteSystem.deleteUser(ulre.getSessionId(), "1");
		assertEquals(deleteRe2.getMessage(), 2, deleteRe2.getReturnCode());		
		//SessionId falsch
		ReturnCodeResponse deleteRe1 = remoteSystem.deleteUser(99999999, "12345678");
		assertEquals(deleteRe1.getMessage(), 1, deleteRe1.getReturnCode());
		//Alles Ok
		ReturnCodeResponse deleteRe = remoteSystem.deleteUser(ulre.getSessionId(), "12345678");
		assertEquals(deleteRe.getMessage(), 0, deleteRe.getReturnCode());
		//Erneuter Login
		UserLoginResponse ulre2 = remoteSystem.login("p@p.de", "12345678");
		assertEquals(ulre2.getMessage(), 2, ulre2.getReturnCode());
	}	
	
	@Test
	public void createLocationScenarios() {
		//Alles ok
		ReturnCodeResponse registerRe = remoteSystem.register("philipp", "p@p.de", "12345678", "12345678");
		assertEquals(registerRe.getMessage(), 0, registerRe.getReturnCode());
		//Alles ok
		UserLoginResponse loginRe = remoteSystem.login("p@p.de", "12345678");
		assertEquals(loginRe.getMessage(), 0, loginRe.getReturnCode());
		//======================createLocationTests========================
		//Alles ok
		ReturnCodeResponse clRe = remoteSystem.createLocation(loginRe.getSessionId(), "Körners", "Kneipe", "Dorfschänke in Arnsberg-Bruchhausen", "Bruchhausenerstr.", "75", 59759, "Arnsberg");
		assertEquals(clRe.getMessage(), 0, clRe.getReturnCode());
		//Sind neue Locations am User gespeichert?
		UserTO user = remoteSystem.getUserDetails(loginRe.getSessionId());
		assertEquals(user.getMessage(), "p@p.de", user.getEmail());
		assertEquals("Hat ein User auch Locations?", "Körners", user.getLocations().get(0).getName());
		//Dieselbe Location nochmal erstellen, sollte abgewiesen werden.
		ReturnCodeResponse clRe2 = remoteSystem.createLocation(loginRe.getSessionId(), "Körners", "Kneipe", "Dorfschänke in Arnsberg-Bruchhausen", "Bruchhausenerstr.", "75", 59759, "Arnsberg");
		assertEquals(clRe2.getMessage(), 2, clRe2.getReturnCode());
		//Falsche SessionId nach Logout
		ReturnCodeResponse logoutRe = remoteSystem.logout(loginRe.getSessionId());
		assertEquals("ReturnCode muss 0 sein.", 0, logoutRe.getReturnCode());	
		ReturnCodeResponse clRe1 = remoteSystem.createLocation(loginRe.getSessionId(), "Körners", "Kneipe", "Dorfschänke in Arnsberg-Bruchhausen", "Bruchhausenerstr.", "75", 59759, "Arnsberg");
		assertEquals(clRe1.getMessage(), 1, clRe1.getReturnCode());
		//wieder einloggen
		UserLoginResponse login = remoteSystem.login("p@p.de", "12345678");
		assertEquals(login.getMessage(), 0, login.getReturnCode());
		//=============================User wieder löschen====================
		ReturnCodeResponse deleteRe = remoteSystem.deleteUser(loginRe.getSessionId(), "12345678");
		assertEquals(deleteRe.getMessage(), 0, deleteRe.getReturnCode());
	}
	
	
	public void listLocationScenarios() {
		CityListResponse cityRe = remoteSystem.listCities();
		assertEquals("ReturnCode muss 0 sein.", 0, cityRe.getReturnCode());
	}
	
}


