package ir.maktab.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static ir.maktab.aspect.CustomerAspect.CYAN_BRIGHT;
import static ir.maktab.aspect.CustomerAspect.RESET;

public class SendEmail {

    public static void sendEmail(String to, String subject, String text) {

        String from = "fatememorovati06@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("fatememorovati06@gmail.com", "george1378");
            }
        });

        session.setDebug(true);

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            System.out.println(CYAN_BRIGHT+"sending email ..."+RESET);
            Transport.send(message);
            System.out.println(CYAN_BRIGHT+"email sent successfully ..."+RESET);
        } catch (MessagingException mex) {
            System.out.println(mex.getCause().toString());
        }

    }
}
