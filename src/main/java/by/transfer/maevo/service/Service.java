package by.transfer.maevo.service;

import by.transfer.maevo.util.SenderMailRu;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static by.transfer.maevo.util.Constant.myMails;

public class Service {
    public List<String> getEmailList() {
        List<String> elements = new ArrayList<>();
        myMails.forEach((key, value) -> elements.add(key));
        return elements;
    }

    public void getFilesFromEmail(String myLogin, String myPassword) {
        SenderMailRu senderMailRu = new SenderMailRu();
        try {
            senderMailRu.getFiles(myLogin, myPassword);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
