package de.noob.noobservice;


import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;

import de.noob.entities.User;

@Stateless
@LocalBean
public class MailerBean {
	
	@Resource(mappedName = "java:/JmsXA")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:/queue/MailOutput")
	private Queue outputQueue;

	public void sendWelcomeMail(User user) {
		try(JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
			TextMessage message = context.createTextMessage();
			message.setStringProperty("DocType", "Email");
			message.setStringProperty("receiver", user.getEmail());
			message.setStringProperty("subject", "Willkommen bei NoobApp!");
			message.setText("Hallo " + user.getName() +", <br/><br/>"
					+ "wir freuen uns dich bei NoobApp begrüßen zu dürfen! <br/>"
					+ "Wenn du mal Hilfe brauchst, schaue doch im HelpCenter vorbei. Es befindet sich direkt in der App. <br/><br/>"
					+ "Viel Spaß wünscht dir dein NoobApp-Team! :-)");
			context.createProducer().send(outputQueue, message);
		}
		catch(JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendLeaveMail(User user) {
		try(JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
			TextMessage message = context.createTextMessage();
			message.setStringProperty("DocType", "Email");
			message.setStringProperty("receiver", user.getEmail());
			message.setStringProperty("subject", "Verlässt du uns wirklich? :'-(");
			message.setText("Hallo " + user.getName() +", <br/><br/>"
					+ "leider hast du deinen Account gelöscht... <br/>"
					+ "Wahrscheinlich war es nur ein Versehen, am Besten du startest gleich die NoobApp und registrierst dich wieder mit der selben Email-Adresse! <br/><br/>"
					+ "Viel Spaß wünscht dir dein NoobApp-Team! :-)");
			context.createProducer().send(outputQueue, message);
		}
		catch(JMSException e) {
			e.printStackTrace();
		}
	}
}
