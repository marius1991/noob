package de.noob.dao;



import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import de.noob.entities.Location;
import de.noob.entities.User;


/**
 * Erzeugt beim Start des Servers Testdaten.
 * @author Tim Hembrock
 *
 */
@Startup
@Singleton
public class DataBuilder {

	private static final Logger logger = Logger.getLogger(DataBuilder.class);
	
	@PersistenceContext
	EntityManager em;
	
	@PostConstruct
	private void init() {
		
		//TestUser anlegen
		User testUser = new User("testuser", "testuser@test.de", "ef797c8118f02dfb649607dd5d3f8c7623048c9c063d532cc95c5ed7a898a64f");
		em.persist(testUser);
		logger.info("User angelegt: " + testUser.getName());
		
		
		//Blaues Haus
		Location blauesHaus = new Location("Blaues Haus", "Bar", "Das “Blaue Haus” ist eine der ältesten Studentenkneipen Münsters.",
				"Kreuzstrasse", "16", 
				48143, 
				"Münster",
				testUser);
		em.persist(blauesHaus);
		logger.info("Location angelegt: " + blauesHaus.getName());
		
		
		//Cavete
		Location cavete = new Location("Cavete", "Bar", "Die akademische Bieranstalt Cavete auf der Kreuzstraße mitten in Münsters Altstadt ist ein Anlaufpunkt für Jung und Alt.",
				"Kreuzstraße", "37/38",
				48143, 
				"Münster", 
				testUser);
		em.persist(cavete);		
		logger.info("Location angelegt: " + cavete.getName());
		
		
		//Ziege
		Location ziege = new Location("Ziege", "Kneipe", "Die Ziege ist die wahrscheinlich kleinste Kneipe der Welt, in jedem Falle die kleinste Kneipe Münsters!",
				"Kreuzstraße", "33 / 34",
				48143,
				"Münster",
				testUser);
		em.persist(ziege);
		logger.info("Location angelegt: " + ziege.getName());
		
		
		//Rewe am York Center
		Location rewe1 = new Location("Rewe (YorkCenter)", "Supermarkt", "Einfacher Supermarkt am Yorkcenter",
				"Catharina-Müller-Straße","4"
				, 48149, 
				"Münster",
				testUser);
		em.persist(rewe1);
		logger.info("Location angelegt: " + rewe1.getName());
		
		
		//Arzt: Chirugie Germania Campus
		Location drMaurer = new Location("Chirugie am Germania Campus","Arzt", "Allgemeinchirurgie, Orthopädie, Unfallchirurgie, Viszeralchirurgie",
				"An der Germania Brauerei","6",
				48159, 
				"Münster",
				testUser);
		em.persist(drMaurer);
		logger.info("Location angelegt: " + drMaurer.getName());
		
		
		//Shell Tankstelle
		Location shell = new Location("Shell", "Tankstelle", "Shell Tankstelle",
				"Weseler Str.", "320",
				48163,
				"Münster",
				testUser);
		em.persist(shell);
		logger.info("Location angelegt: " + shell.getName());
		
		
		//Friseur
		Location friseur = new Location("Junge Köpfe", "Friseur", "Frisör",
				"An der Germania Brauerei", "3", 
				48159, 
				"Münster",
				testUser);
		em.persist(friseur);
		logger.info("Location angelegt: " + friseur.getName());
		
		
		//Disko Veron
		Location veron = new Location("Veron", "Diskothek", "Die mit Ledersofas, floralen Tapeten und unzähligen LEDs edel gestylte Disco bietet Black- und House-Partys.",
				"Albersloher Weg", "14", 
				48155,
				"Münster",
				testUser);
		em.persist(veron);
		logger.info("Location angelegt: " + veron.getName());
		
		
		//FHZ
		Location fhz = new Location("Fachhochschul Zentrum","Hochschule/Universität", "Das Fachhochschulzentrum bietet gleich Platz für 3 Fachbereiche: Wirtschaft, Bauingenieurwesen und Oecotrophologie/ Facility Management",
				"Corrensstrasse", "25",
				48149,
				"Münster",
				testUser);
		em.persist(fhz);
		logger.info("Location angelegt: " + fhz.getName());
		
		//Gymnasium
		Location scholl = new Location("Geschwister-Scholl-Gymnasium","Schule","Gymnasium",
				"Von-Humboldt-Straße", "14",
				48159,
				"Münster",
				testUser);
		em.persist(scholl);
		logger.info("Location angelegt: " + scholl.getName());
		
		
		//Volksbank
		Location bank = new Location("Volksbank Münster", "Bank", "Hauptzentrale Münster",
				"Neubrückenstraße", "66",
				48143,
				"Münster",
				testUser);
		em.persist(bank);
		logger.info("Location angelegt: " + bank.getName());
	
		
		//Schlosspark
		Location schlosspark = new Location("Schlosspark", "Park", "Park am Schloss",
				"Schlossgarten" ,"4",
				48149,
				"Münster",
				testUser);
		em.persist(schlosspark);
		logger.info("Location angelegt: " + schlosspark.getName());
		
		
		//Dom in Münster
		Location dom = new Location("Münster Dom","Kirche", "Dom in Münster",
				"Domplatz", "28",
				48143,
				"Münster",
				testUser);
		em.persist(dom);
		logger.info("Location angelegt: " + dom.getName());
		
		
		//Schloss
		Location schloss = new Location("Münster Schloss", "Sehenswürdigkeit", "Schloss in Münster",
				"Schlossplatz", "2",
				48149, 
				"Münster",
				testUser);
		em.persist(schloss);
		logger.info("Location angelegt: " + schloss.getName());
	}
	
}
