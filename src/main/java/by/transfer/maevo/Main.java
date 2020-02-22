package by.transfer.maevo;

import by.transfer.maevo.Pojo.HostPortInfo;
import by.transfer.maevo.Pojo.SessionStoreFolder;
import by.transfer.maevo.facade.Facade;
import by.transfer.maevo.facade.FacadeImpl;
import by.transfer.maevo.facade.UtilClass;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.mail.*;
import java.util.List;
import java.util.Properties;

import static by.transfer.maevo.util.Constant.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        UtilClass utilClass = new UtilClass();
        FacadeImpl facade = new Facade();

        List<String> emails = utilClass.getEmailList();
        List<String> protocols = utilClass.getProtocolList();

        ObservableList<String> emailList = FXCollections.observableArrayList(emails);
        ComboBox<String> emailBox = new ComboBox<>(emailList);
        emailBox.setValue(emails.get(0));

        ObservableList<String> protocolList = FXCollections.observableArrayList(protocols);
        ComboBox<String> protocolBox = new ComboBox<>(protocolList);
        protocolBox.setValue(protocols.get(0));

        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 300, 200);
        Button button = new Button(BUTTON_NAME);

        grid.add(new FlowPane(150, 150, emailBox), 0, 2);
        grid.add(new FlowPane(150, 150, protocolBox), 0, 3);
        utilClass.fillScene(grid, SCENE_NAME);
        utilClass.fillGrid(grid);
        utilClass.createButton(grid, button, 4);

        button.setOnAction(
                event -> {
                    String protocol = protocolBox.getValue();
                    String email = emailBox.getValue();
                    String password = myMails.get(emailBox.getValue());
                    HostPortInfo hostPortInfo = facade.getFilesFromEmail(email, password, protocol);

                    Properties properties = utilClass.getServerProperties(
                            protocol,
                            hostPortInfo.getHost(),
                            hostPortInfo.getPort(),
                            hostPortInfo.getSslTrust()
                    );
// TODO: 2/16/2020 imap moved files to basket, but pop3 deleted files totally from email post!!!
                    // TODO: 2/16/2020 imap видит все папки через
                    //  Folder [] folders = store.getDefaultFolder().list();
                    //            for (Folder fold: folders) {
                    //                System.out.println(fold.getName());
                    //            }

                    SessionStoreFolder sessionStoreFolder = facade.getSessionStoreFolder(properties, protocol, email, password);
                    Message[] messages = facade.getMessages(sessionStoreFolder);

                    String destFilePath = "C:/Users/Maevskiy/Desktop/files/";
                    facade.saveFile(messages, destFilePath);
                    facade.closeSessionStoreFolder(sessionStoreFolder);
                }
        );
        stage.setTitle(TITLE_NAME);
        stage.setScene(scene);
        stage.show();
    }
}