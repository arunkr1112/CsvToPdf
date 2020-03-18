package com.stanra.csvtopdf;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Hello world!
 *
 */
	public class SendAttachment
		{ 
		
    	public static void Mailmain(String path, String email, String name) {
            // SMTP info
            String host = "smtp.gmail.com";
            String port = "587";
            String mailFrom = "arun.kumar@stanratech.com";		//your-email-address
            String password = "labbfuvxnkqwohns";	//your-email-password
     
            // message info
            String mailTo = email;  //your-friend-email
            String subject = "Sallery Slip";	//New email with attachments
            String message = "This is your sallery slip.";
     
            // attachments
		/*
		 * String[] attachFiles = new String[2]; attachFiles[0] =
		 * "/home/stanratech/Downloads/images.jpeg"; attachFiles[1] =
		 * "/home/stanratech/Downloads/images2.jpeg";
		 */
           // String path = "/home/stanratech/Downloads/images.jpeg";
            System.out.print("Sending mail to " + name + "...");
     
            try {
            	EmailAttachmentSender.sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
                    subject, message, path);
                System.out.println(" sent!");
            } catch (Exception ex) {
                System.out.println("Could not send email.");
                ex.printStackTrace();
            }
        }
    
          
    }
 class EmailAttachmentSender {
	 
    public static void sendEmailWithAttachments(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String attachFile)
            throws AddressException, MessagingException {
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFile != null ) {
            
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(attachFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }}
