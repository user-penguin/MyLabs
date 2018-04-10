import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Palindroms {
    public static String getString(){
        String retText = new String();
        try{
            FileInputStream fstream = new FileInputStream("/home/dmitry/Documents/development/MyLabs/MyLabs/resources/AlgoDynamic7.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            retText = br.readLine();
        }catch (IOException e){
            System.out.println("Ошибка");
        }
        return retText;
    }

    public static void main(String[] args){
        String text = getString();
        if(text == null){
            System.out.println("Исходный файл пуст");
            return;
        }
        else {
            System.out.format("Исходная последовательность = %s\n", text);
        }
    }
}
