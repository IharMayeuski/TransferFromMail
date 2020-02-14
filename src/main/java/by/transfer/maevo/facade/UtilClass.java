package by.transfer.maevo.facade;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static by.transfer.maevo.util.Constant.myMails;
import static by.transfer.maevo.util.Constant.protocols;
import static java.util.Objects.nonNull;

public class UtilClass {
    public Properties getServerProperties(String protocol, String host, String port, String sslTrust) {
        Properties properties = new Properties();
        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        // SSL setting
        properties.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
        properties.setProperty(String.format("mail.%s.socketFactory.port", protocol), port);
        properties.put(sslTrust, "*");
        return properties;
    }

    public String parseAddresses(Address[] address) {
        StringBuilder sb = new StringBuilder();
        if (nonNull(address)) {
            for (Address addressOne: address) {
                sb.append(addressOne.toString() + ", ");
            }
            if (sb.length() > 1) {
                sb.delete((sb.length() - 2), sb.length());
            }
        }
        return sb.toString();
    }

    public List<String> getEmailList() {
        List<String> elements = new ArrayList<>();
        myMails.forEach((key, value) -> elements.add(key));
        return elements;
    }

    public List<String> getProtocolList() {
        return protocols;
    }

    public void fillGrid(GridPane grid) {
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
    }

    public void fillScene(GridPane grid, String sceneName) {
        Text scenetitle = new Text(sceneName);
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
    }

    public void createButton(GridPane grid, Button button, int row) {
        HBox hBox = new HBox(40);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        hBox.getChildren().add(button);
        grid.add(hBox, 0, row);
    }
}