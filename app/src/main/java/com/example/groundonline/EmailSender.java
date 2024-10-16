// EmailSender.java
package com.example.groundonline;

import android.util.Log;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSender {

    private String email;
    private String password;

    public EmailSender(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void sendEmail(String to, String subject, String messageBody) throws MessagingException {
        // SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP host (change if using a different provider)
        props.put("mail.smtp.port", "587"); // TLS port (change if using a different provider)

        // Create a session with authentication
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(email));

            // Set To: header field
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(messageBody);

            // Send message
            Transport.send(message);

            Log.d("EmailSender", "Email sent successfully to: " + to);
        } catch (MessagingException e) {
            Log.e("EmailSender", "Error sending email to: " + to, e);
            throw e; // Re-throw exception to handle it in the calling method
        }
    }
}
