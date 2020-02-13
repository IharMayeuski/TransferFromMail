package by.transfer.maevo.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.util.Properties;

public class SenderMailRu {
    private final String AUTHENTIFICATION ="mail.smtp.auth";
    private final String STARTTLS ="mail.smtp.starttls.enable";
    private final String TRUE = "true";
    private final String HOST = "mail.smtp.host";
    private final String MAIL_RU = "smtp.mail.ru";
    private final String PORT = "mail.smtp.port";
    private final String PORT_NUMBER = "587";

    private Properties properties;

    public SenderMailRu() {
        properties = new Properties();
        properties.put(AUTHENTIFICATION, TRUE);
        properties.put(STARTTLS, TRUE);
        properties.put(HOST,MAIL_RU);
        properties.put(PORT, PORT_NUMBER);
        properties.put("mail.smtp.ssl.trust", MAIL_RU);
    }

    public void getFiles(String email, String password) throws MessagingException {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        Store store = session.getStore("pop3");
        store.connect(email, password);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("maevskij.i@gmail.com"));
        message.setSubject("222");
        message.setText("111111111");
        Transport.send(message);
    }

/*        Store store = session.getStore("pop3");//create store instance
        store.connect(HOST, email, password);
        Folder inbox = store.getFolder("inbox");
        FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
        inbox.open(Folder.READ_ONLY);//set access type of Inbox
        Message messages[] = inbox.search(ft);
        System.out.println(messages);*/


//        Properties props = System.getProperties();
//        props.setProperty("mail.store.protocol", "imaps");
//        Session session2 = Session.getDefaultInstance(props, null);
//        Store store2 = session.getStore("imaps");
//        store2.connect("imap.mail.ru", "parser_my_cv@mail.ru", "yamolodex12");
//        System.out.println(store2);

 /*       Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        try {
            Session session = Session.getDefaultInstance(props, null);
            javax.mail.Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "maevskij.i@gmail.com", "Ii98729872gmail");
            javax.mail.Folder[] folders = store.getDefaultFolder().list("*");
            for (javax.mail.Folder folder : folders) {
                if ((folder.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
                    System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }*/
}
