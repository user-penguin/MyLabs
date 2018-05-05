package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button myButton;

    @FXML
    private TextArea myTextArea;

    @FXML
    private TextField id;


    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).

    }

    // When user click on myButton
    // this method will be called.
    public void showDateTime(ActionEvent event) {
        System.out.println("Button Clicked!");

        String JSON_STRING;
        String usId = id.getText();
        String json_url = "https://api.vk.com/method/users.get?" + "user_ids=" +
                usId
                + "&fields=contacts,bdate,city,education&v=5.74";

        String finText = "";

        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }

            JSONObject info = new JSONObject(stringBuilder.toString());

            JSONArray obj = info.getJSONArray("response");
            JSONObject info1 = (JSONObject) obj.get(0);

            //получаем телефон
            String phone = "";
            try {
                phone = (String) info1.get("mobile_phone");
            } catch (JSONException e) {
            }

            //получаем имя и фамилию
            String first_name = (String) info1.get("first_name");
            String last_name = (String) info1.get("last_name");

            //получаем город
            String city = "";
            try {
                JSONObject info2 = (JSONObject) info1.get("city");
                city = (String) info2.get("title");
            } catch (JSONException e) {
            }

            //универ и факультет
            String uni = "";
            String fuc = "";
            try {
                uni = (String) info1.get("university_name");
                fuc = (String) info1.get("faculty_name");
            } catch (JSONException e) {
            }

            //дата рождения
            String bdate = "";
            try {
                bdate = (String) info1.get("bdate");
            } catch (JSONException e) {
            }

            //вывод информации
            finText += first_name + " " + last_name;
            if(city.length() > 0)
                finText += " was born in " + city;
            if(bdate.length() > 0)
                finText += " in " + bdate;
            if(uni.length() > 0){
                finText += " протирает штаны в " + uni;
                if(fuc.length() > 0)
                    finText += " на " + fuc;
            }
            if(phone.length() > 0)
                finText += "Подышать в ушко можно сюда: " + phone;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Show in VIEW
        myTextArea.setText(finText);



    }
}
