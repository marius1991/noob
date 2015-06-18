package de.noob.mail;

import java.util.Date;
import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message.RecipientType;

import org.jboss.logging.Logger;


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
	
   /* @Resource(name = "java:/Mail")  
    private Session mailSession;  */
	
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			logger.info("Message: " + msg.getText());
			logger.info("Message erhalten.");
			final String username = "info.noobapp@gmail.com";
			final String password = "wirsindnoobs";
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			
			Session session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });
			
			MimeMessage m = new MimeMessage(session);
			m.setFrom("info.noobapp@gmail.com");
			m.setRecipient(RecipientType.TO, new InternetAddress(msg.getStringProperty("receiver")));
			m.setSubject(msg.getStringProperty("subject"));
			m.setSentDate(new Date());
			m.setContent(msg.getText(), "text/html; charset=UTF-8");
			Transport.send(m);
		}
		catch(JMSException e){
			throw new EJBException(e);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
