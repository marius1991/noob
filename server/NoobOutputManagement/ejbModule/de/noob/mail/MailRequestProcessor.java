package de.noob.mail;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;

import org.jboss.logging.Logger;

/**
 * Diese MessageDrivenBean empfängt die Mails aus der Queue "MailOutput" und erstellt eine MimeMessage.
 * Die MimeMessage wird schliesslich zum GoogleMail SMTP-Server gesendet.
 * @author Philipp Ringele
 *
 */
@MessageDriven(
		activationConfig = {
				@ActivationConfigProperty(
						propertyName = "destinationType",
						propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
						propertyName = "destination",
						propertyValue = "queue/MailOutput"),
				@ActivationConfigProperty(
						propertyName = "messageSelector",
						propertyValue = "DocType LIKE 'Email'") })
public class MailRequestProcessor implements MessageListener {

	private static final Logger logger = Logger.getLogger(MailRequestProcessor.class);
		
	/**
	 * Session-Daten ausgelagert, damit diese konfiguriert werden können ohne den Quelltext zu verändern 
	 * und neu kompilieren zu müssen.
	 */
	 @Resource(name = "java:jboss/mail/Gmail") 
	 private Session session;
	
	/**
	 * Holt TextMessages aus der Queue ab und erstellt aus ihnen E-Mails,
	 * die zum Gmail SMTP-Server gesendet werden.
	 */
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			logger.info("Message: " + msg.getText());
			logger.info("Message erhalten.");
			
			MimeMessage m = new MimeMessage(session);
			m.setFrom("info.noobapp@gmail.com");
			m.setRecipient(RecipientType.TO, new InternetAddress(msg.getStringProperty("receiver")));
			m.setSubject(msg.getStringProperty("subject"));
			m.setSentDate(new Date());
			m.setContent(msg.getText(), "text/html; charset=UTF-8");
			Transport.send(m);
			logger.info("Email gesendet.");
		}
		catch(JMSException e){
			throw new EJBException(e);
		} 
		catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
