package app.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;

/**
 * Created by Romeo on 6/16/2017.
 */


public class PostMail {

    public void postMail(String recipients[], String subject, String message) throws MessagingException {
        boolean debug = false;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

//        Session session = Session.getDefaultInstance(props, null);
//        new javax.mail.Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("goalkeeper.lig360@gmail.com", "goalkeeper360");
//            }
//        };
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication  getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                "goalkeeper.lig360@gmail.com", "goalkeeper360");
                }
                });
        session.setDebug(debug);

        Message objMessage = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress("goalkeeper.lig360@gmail.com");
        objMessage.setFrom(addressFrom);

        InternetAddress[] objAddressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < objAddressTo.length; i++) {
            objAddressTo[i] = new InternetAddress(recipients[i]);
        }
        objMessage.setRecipients(Message.RecipientType.TO, objAddressTo);
        objMessage.addHeader("MyHeaderName", "myHeaderValue");

        objMessage.setSubject(subject);
        objMessage.setContent(message, "text/html; charset=utf-8");
        Transport.send(objMessage);
        System.out.println("Message is send properly:");
    }
}