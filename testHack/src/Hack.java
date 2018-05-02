import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.*;

public class Hack {

    public static void main(String[] args){
        System.out.println("Введите id пользователя");
        String id_user = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            id_user = br.readLine();
        }
        catch (IOException e) {
          //  e.printStackTrace();
        }

        String JSON_STRING;

        String json_url = "https://api.vk.com/method/users.get?" + "user_ids=" +
                id_user
                + "&fields=contacts,bdate,city,education&v=5.74";
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while((JSON_STRING = bufferedReader.readLine()) != null){
                stringBuilder.append(JSON_STRING + "\n");
            }

            JSONObject info = new JSONObject(stringBuilder.toString());

            JSONArray obj = info.getJSONArray("response");
            JSONObject info1 = (JSONObject) obj.get(0);

            //получаем телефон
            String phone = "";
            try {
                phone = (String) info1.get("mobile_phone");
            }
            catch (JSONException e){
            }

            //получаем имя и фамилию
            String first_name = (String) info1.get("first_name");
            String last_name = (String) info1.get("last_name");

            //получаем город
            String city = "";
            try {
                JSONObject info2 = (JSONObject) info1.get("city");
                city = (String) info2.get("title");
            }
            catch (JSONException e){
            }

            //универ и факультет
            String uni = "";
            String fuc = "";
            try {
                uni = (String) info1.get("university_name");
                fuc = (String) info1.get("faculty_name");
            }
            catch (JSONException e){
            }

            //дата рождения
            String bdate = "";
            try{
                bdate = (String) info1.get("bdate");
            }
            catch (JSONException e){
            }

            //вывод информации
            System.out.print(first_name + " " + last_name);
            if(city.length() > 0)
                System.out.print(" was born in " + city);
            if(bdate.length() > 0)
                System.out.print(" in " + bdate);
            if(uni.length() > 0){
                System.out.print(" протирает штаны в " + uni);
                if(fuc.length() > 0)
                    System.out.print(" на " + fuc);
            }
            if(phone.length() > 0)
                System.out.println("Подышать в ушко можно сюда: " + phone);

        }
        catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}
