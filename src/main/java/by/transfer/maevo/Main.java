package by.transfer.maevo;


import by.transfer.maevo.service.Service;
import by.transfer.maevo.service.ServiceFillScene;
import by.transfer.maevo.util.EmailReceiver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import static by.transfer.maevo.util.Constant.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServiceFillScene fillSceneService = new ServiceFillScene();
        EmailReceiver emailReceiver = new EmailReceiver();
        Service service = new Service();
        List<String> elements = service.getEmailList();

        ObservableList<String> langs = FXCollections.observableArrayList(elements);
        ComboBox<String> langsComboBox = new ComboBox<>(langs);
        langsComboBox.setValue(elements.get(0));

        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 200, 160);
        Button button = new Button(BUTTON_NAME);

        grid.add(new FlowPane(150, 150, langsComboBox), 0, 2);
        fillSceneService.fillScene(grid, SCENE_NAME);
        fillSceneService.fillGrid(grid);
        fillSceneService.createButton(grid, button, 3);

        button.setOnAction(event -> {
                    emailReceiver.getFilesFromEmail(langsComboBox.getValue(), myMails.get(langsComboBox.getValue()));

                }
        );
        stage.setTitle(TITLE_NAME);
        stage.setScene(scene);
        stage.show();
    }
}