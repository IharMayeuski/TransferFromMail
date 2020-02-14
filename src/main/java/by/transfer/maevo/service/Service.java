package by.transfer.maevo.service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Service implements ServiceImpl{
    public void safeFileOnLaptop(Multipart multiPart, String filePath) throws MessagingException {
        for (int j = 0; j < multiPart.getCount(); j++) {
            MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(j);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                String fileName = part.getFileName().split("@")[0];
                String destFilePath = filePath + fileName;
                try {
                    FileOutputStream output = new FileOutputStream(destFilePath);
                    InputStream input = part.getInputStream();
                    byte[] buffer = new byte[4096];
                    int byteRead;
                    while ((byteRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, byteRead);
                    }
                    output.close();
                } catch (FileNotFoundException e) {
                    System.out.println("UPS1");
                } catch (IOException e) {
                    System.out.println("UPS2");
                }
            }
        }
    }
}
