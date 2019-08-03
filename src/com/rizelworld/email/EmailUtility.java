package com.rizelworld.email;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
public class EmailUtility {
	
	static final Logger LOGGER = Logger.getLogger(EmailUtility.class);
	
	public static void sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {
		
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.socketFactory.port", port);  
        properties.put("mail.smtp.socketFactory.class",  
                  "javax.net.ssl.SSLSocketFactory"); 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance((Properties)properties, (Authenticator)auth);
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom((Address)new InternetAddress(userName));
            InternetAddress[] toAddresses = new InternetAddress[]{new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, (Address[])toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setContent((Object)message, "text/html");
            Transport.send((Message)msg);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            LOGGER.error(e);
            throw new RuntimeException((Throwable)e);
        }
    }

}
