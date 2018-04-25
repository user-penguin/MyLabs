//Сравнить по быстродействию алгоритм Кнута-Морриса-Пратта и алгоритм Бойера-Мура
//при поиске в тексте образца, все символы которого различны.

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpeedTest {

    //считываем строку
    String readStartString(){
        String text = new String();
        try{
            FileInputStream fstream = new FileInputStream("/home/dmitry/" +
                    "Documents/development/MyLabs/MyLabs/resources/string.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String rd;
            while((rd = br.readLine()) != null){
                text += rd;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void main(String[] args){
        SpeedTest mn = new SpeedTest();
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));


        String text; //исходная строка
        String incl_one = "";
        String incl_two = "";       //подстроки которые будем искать
        String incl_three = "";

        System.out.println("Введите три подстроки, которые будем искать в тексте");
        try{
            incl_one = rd.readLine();
            incl_two = rd.readLine();
            incl_three = rd.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        long startTime = System.currentTimeMillis();
        text = mn.readStartString();
        long stopTime = System.currentTimeMillis();
        long result = stopTime - startTime;
        System.out.println(result);
        
        System.out.println("String done!");
    }
}
