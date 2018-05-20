package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;


public class Controller {
    @FXML
    private TextArea txtArea1 = new TextArea();

    private connect myConnect =  new connect("localhost", 1521, "xe", "user-penguin", "1");

    @FXML
    private TextField txtField1 = new TextField();

    @FXML
    private VBox sellervbox = new VBox();

    public Controller(){}

    //отрисовка после старта
    public void firstDraw(ActionEvent actionEvent){
        String[] fromTable = {"Name"};
        ArrayList<String> sellers = new ArrayList<>();
        sellers = myConnect.resultSelect("select \"Name\" from \"Seller\" ", fromTable);
        ArrayList<CheckBox> chBxsSel = new ArrayList<>();
        for(int i = 0; i < sellers.size(); i++)
            chBxsSel.add(new CheckBox(sellers.get(i)));

        for(int i = 0; i < chBxsSel.size(); i++)
            sellervbox.getChildren().add(chBxsSel.get(i));
    }

    public void firstButtonAction(ActionEvent actionEvent) {
        String[] fromTable = {"Name"};
        myConnect.select("select \"Name\" from \"CatOfProd\" ", fromTable);

    }
}
