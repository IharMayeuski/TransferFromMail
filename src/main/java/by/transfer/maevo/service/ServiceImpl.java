package by.transfer.maevo.service;

import javax.mail.MessagingException;
import javax.mail.Multipart;

public interface ServiceImpl {
    public void safeFileOnLaptop(Multipart multiPart, String filePath) throws MessagingException;
}
