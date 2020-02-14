package by.transfer.maevo.facade;

import by.transfer.maevo.Pojo.HostPortInfo;
import by.transfer.maevo.Pojo.SessionStoreFolder;
import by.transfer.maevo.service.Service;
import by.transfer.maevo.service.ServiceImpl;

import javax.mail.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Facade implements FacadeImpl {
    public HostPortInfo getFilesFromEmail(String userName, String password, String protocol) {
        HostPortInfo hostPortInfo = new HostPortInfo();
        String host = protocol.equals("pop3")
                ? "pop." + userName.split("@")[1]
                : "imap." + userName.split("@")[1];
        String sslTrust = protocol.equals("pop3")
                ? "mail.pop3.ssl.trust"
                : "mail.imap.ssl.trust";
        String port = protocol.equals("pop3")
                ? "995"
                : "993";
        hostPortInfo.setHost(host);
        hostPortInfo.setSslTrust(sslTrust);
        hostPortInfo.setPort(port);
        return hostPortInfo;
    }

    public void saveFile(Message[] messages, String filePath) {
        UtilClass utilClass = new UtilClass();
        ServiceImpl service = new Service();
        try {
            for (int i = 0; i < messages.length; i++) {
                Message msg = messages[i];
                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String toList = utilClass.parseAddresses(msg.getRecipients(Message.RecipientType.TO));
                String ccList = utilClass.parseAddresses(msg.getRecipients(Message.RecipientType.CC));
                String sentDate = msg.getSentDate().toString();
                String contentType = msg.getContentType();
                if (contentType.contains("multipart")) {
                    try {
                        Multipart multiPart = (Multipart) msg.getContent();
                        service.safeFileOnLaptop(multiPart, filePath);
                    } catch (IOException e) {
                        System.out.println("UPS0");
                    }
                }

                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t To: " + toList);
                System.out.println("\t CC: " + ccList);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
            }
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
    }

    @Override
    public SessionStoreFolder getSessionStoreFolder(Properties properties, String protocol, String email, String password) {
        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore(protocol);
            store.connect(email, password);
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
            return new SessionStoreFolder(session, store, folder);
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
        }
        return null;
    }

    @Override
    public void closeSessionStoreFolder(SessionStoreFolder sessionStoreFolder) {
        try {
            sessionStoreFolder.getFolder().close(false);
            sessionStoreFolder.getStore().close();
        }catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
        }
    }

    @Override
    public Message[] getMessages(SessionStoreFolder sessionStoreFolder) {
        try {
            return sessionStoreFolder.getFolder().getMessages();
        }catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
        }
        return null;
    }
}
