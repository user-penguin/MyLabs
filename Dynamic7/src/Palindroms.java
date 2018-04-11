///Дана строка из заглавных латинских букв. Необходимо найти длину самого большого палиндрома,
///который можно получить вычёркиванием некоторых букв из данной строки.

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    public static void makePares(ArrayList<Pare> Pares, ArrayList<Record> ABC, int num){
        int lenght = ABC.get(num).nums.size();
        for(int i = 0; i < lenght - 1; i++){
            for(int j = i + 1; j < lenght; j++){
                Pares.add(new Pare(ABC.get(num).sym, ABC.get(num).nums.get(i), ABC.get(num).nums.get(j)));
            }
        }
    }

    public static void main(String[] args){
        String text = getString();
        if (text == null){
            System.out.println("Исходный файл пуст");
            return;
        }
        else {
            System.out.format("Исходная последовательность = %s\n", text);
        }

        // вычленение алфавита
        ArrayList<Record> AlphaBet = new ArrayList<>(); // сам алфавит
        char[] textInChar = text.toCharArray();
        ArrayList<Integer> notUsedSym = new ArrayList<>();
        for (int i = 0; i < textInChar.length; i++)
            notUsedSym.add(i);

        // обход символов и создание алфавита
        while (notUsedSym.size() > 0){
            AlphaBet.add(new Record(textInChar[notUsedSym.get(0)], notUsedSym.get(0)));
            notUsedSym.remove(0);
            int currentNum = AlphaBet.size() - 1;
            for (int i = 0; i < notUsedSym.size(); i++){
                if(textInChar[notUsedSym.get(i)] == AlphaBet.get(currentNum).sym){
                    AlphaBet.get(currentNum).nums.add(notUsedSym.get(i));
                    notUsedSym.remove(i);
                    i--;
                }
            }
        }
        System.out.println("Alphabet done!");

        // разобьём на пары
        ArrayList<Pare> Pares = new ArrayList<>();
        for(int i = 0; i < AlphaBet.size(); i++){
            makePares(Pares, AlphaBet, i);
        }
        System.out.println("Pares done!");
    }
}
