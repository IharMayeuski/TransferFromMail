package by.transfer.maevo.facade;

import by.transfer.maevo.Pojo.HostPortInfo;
import by.transfer.maevo.Pojo.SessionStoreFolder;

import javax.mail.Message;
import java.util.Properties;

public interface FacadeImpl {
    HostPortInfo getFilesFromEmail(String userName, String password, String protocol);

    void saveFile(Message[] messages, String filePath);

    SessionStoreFolder getSessionStoreFolder(Properties properties, String protocol, String email, String password);

    void closeSessionStoreFolder(SessionStoreFolder sessionStoreFolder);

    Message[] getMessages(SessionStoreFolder sessionStoreFolder);

}
