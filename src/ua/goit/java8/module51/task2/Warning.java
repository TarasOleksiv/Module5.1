package ua.goit.java8.module51.task2;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import ua.goit.java8.module51.task1.*;

/**
 * Created by t.oleksiv on 01/08/2017.
 */
public class Warning {
    private Label warningLabel;
    private Pane root;

    public Warning(Pane root){
        this.root = root;
        //generateWarning();
    }

    // Генерація попередження
    public Label generateWarning(){
        Label warningLabel = new Label("Зірка не вміщається на картинці!");
        warningLabel.setLayoutX(Main.WIDTH/6);
        warningLabel.setLayoutY(Main.HEIGHT/3);
        warningLabel.setFont(new Font("Arial", 30));
        return warningLabel;
    }

    // метод для стирання попередження
    public void clearWarnings(){
        if (warningLabel != null){
            root.getChildren().removeAll(warningLabel);
        }
    }
}
