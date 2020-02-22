package by.transfer.maevo.facade;

import by.transfer.maevo.Pojo.HostPortInfo;
import by.transfer.maevo.Pojo.SessionStoreFolder;
import by.transfer.maevo.service.Service;
import by.transfer.maevo.service.ServiceImpl;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import static java.util.Objects.nonNull;

public class Facade implements FacadeImpl {
    public HostPortInfo getFilesFromEmail(String userName, String password, String protocol) {
        String name = userName.split("@")[1];
        HostPortInfo hostPortInfo = new HostPortInfo();
        String host = protocol.equals("pop3")
                ? "pop." + name
                : "imap." + name;
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
        for (int i = messages.length - 1; i >= 0; i--) {
            Message msg = messages[i];
            try {
                // TODO: 2/17/2020 Удаление письма, установка флага
//                msg.setFlag(Flags.Flag.DELETED, true);

                Address[] fromAddress = msg.getFrom();
                String from = fromAddress[0].toString();
                String subject = msg.getSubject();
                String toList = utilClass.parseAddresses(msg.getRecipients(Message.RecipientType.TO));
                String ccList = utilClass.parseAddresses(msg.getRecipients(Message.RecipientType.CC));
                String sentDate = nonNull(msg.getSentDate())
                        ? msg.getSentDate().toString()
                        : "";
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
            } catch (AddressException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException ex) {
                System.out.println("No provider for protocol");
                ex.printStackTrace();
            } catch (MessagingException ex) {
                System.out.println("Could not connect to the message store");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public SessionStoreFolder getSessionStoreFolder(Properties properties, String protocol, String email, String password) {
        try {
            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore(protocol);
            store.connect(email, password);
            Folder [] folders = store.getDefaultFolder().list();
            for (Folder fold: folders) {
                System.out.println(fold.getName());
            }
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
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
        Folder folder = sessionStoreFolder.getFolder();
        Store store = sessionStoreFolder.getStore();
        try {
            if (nonNull(folder)) {
                folder.close(true);
            }
            if (nonNull(store)) {
                store.close();
            }
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
        }
    }

    @Override
    public Message[] getMessages(SessionStoreFolder sessionStoreFolder) {
        try {
            return sessionStoreFolder.getFolder().getMessages();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
        }
        return null;
    }
}
