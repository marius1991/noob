package de.fh_muenster.noob.noobservice;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.jboss.logging.Logger;
import org.jboss.ws.api.annotation.WebContext;

import de.fh_muenster.noob.util.DtoAssembler;



/**
 * 
 * @author philipp
 *
 */
//@WebService
//@WebContext(contextRoot="/noob")
@Stateless
public class NoobOnlineIntegration {

	private static final Logger logger = Logger.getLogger(NoobOnlineIntegration.class);
	
	
	
	/**
	 * EJB zur Erzeugung von DataTransferObjects
	 */
	@EJB
	private DtoAssembler dtoAssembler;
	
	
	public NoobOnlineIntegration() {
		
	}

}
