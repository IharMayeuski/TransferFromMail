package by.transfer.maevo.service;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ServiceFillScene {
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
