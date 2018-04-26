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
        BoyerMoore BM = new BoyerMoore();
        KMP kmp = new KMP();

        String text; //исходная строка
        text = mn.readStartString();


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
        BM.findPattern(text, incl_one, 0);
        BM.findPattern(text, incl_two, 0);
        BM.findPattern(text, incl_three, 0);
        long stopTime = System.currentTimeMillis();
        long result = stopTime - startTime;
        System.out.println("Результат Бойера-Мура = " + result);

        startTime = System.currentTimeMillis();
        kmp.FindKMP(incl_one, text);
        kmp.FindKMP(incl_two, text);
        kmp.FindKMP(incl_three, text);
        stopTime = System.currentTimeMillis();
        result = stopTime - startTime;
        System.out.println("Результат Кнута-Морриса-Пратта = " + result);
    }
}
