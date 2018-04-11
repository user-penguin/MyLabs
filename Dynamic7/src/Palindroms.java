///Дана строка из заглавных латинских букв. Необходимо найти длину самого большого палиндрома,
///который можно получить вычёркиванием некоторых букв из данной строки.

import javafx.animation.StrokeTransition;

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

    public static boolean possible(ArrayList<Pare> toPaste, Pare fromPaste){
        boolean[] flags = new boolean[toPaste.size()];
        for(int i = 0; i < flags.length; i++)
            flags[i] = false;

        for(int i = 0; i < toPaste.size(); i++){
            if(fromPaste.first < toPaste.get(i).first && fromPaste.last > toPaste.get(i).last ||
                    fromPaste.first > toPaste.get(i).first && fromPaste.last < toPaste.get(i).last)
                flags[i] = true;
        }

        boolean finFlag = true;
        for(int i = 0; i < flags.length; i++)
            if(flags[i] == false)
                finFlag = false;

        return finFlag;
    }

    public static int[] getIndexes(ArrayList<Pare> pares){
        int[] indexes = new int[2];
        int num = 0;
        int min = 0;
        for(int i = 0; i < pares.size(); i++){
            if(pares.get(i).first > min){
                num = i;
                min = pares.get(i).first;
            }
        }
        indexes[0] = pares.get(num).first;
        indexes[1] = pares.get(num).last;

        return indexes;
    }

    public static String findSym(ArrayList<Pare> pares, boolean incl, int num, char[] text, int lenPalin){
        int[] numbers = new int[text.length];
        for(int i = 0; i < pares.size(); i++){
            numbers[pares.get(i).first] = 1;
            numbers[pares.get(i).last] = 1;
        }
        if(incl)
            numbers[num] = 1;

        String palin = new String();
        String messg = new String();
        messg += "\nБуквы, которые стоит исключить: \n";
        for(int i = 0; i < numbers.length; i++){
            if(numbers[i] == 1)
                palin += text[i];
            if(numbers[i] == 0){
                messg += text[i] + " на месте " + Integer.toString(i) + "\n";
            }
        }
        messg += palin;
        return messg;
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

        if(Pares.size() == 0 && text.length() > 1)
            System.out.println("Палиндромов нет вовсе!");
        else if(Pares.size() == 0 && text.length() == 1){
            System.out.println("Палиндром = " + text);
        }

        else {
            // вычислим скрытые палиндромы
            ArrayList<ArrayList<Pare>> finHidPalindroms = new ArrayList<>();
            finHidPalindroms.add(new ArrayList<Pare>());
            int cursorBeg = 0;
            int cursorEnd = 0;
            boolean doing = true;
            while (doing) {
                for (int i = 0; i < Pares.size(); i++) {
                    if (possible(finHidPalindroms.get(cursorBeg), Pares.get(i))) {
                        finHidPalindroms.add(new ArrayList<Pare>());
                        cursorEnd++;
                        finHidPalindroms.get(cursorEnd).addAll(finHidPalindroms.get(cursorBeg));
                        finHidPalindroms.get(cursorEnd).add(Pares.get(i));
                    }
                }
                cursorBeg++;

                if (cursorBeg > cursorEnd)
                    doing = false;
            }


            // посчитаем длину максимального палиндрома
            int maxLenght = 0;
            for (int i = 0; i < finHidPalindroms.size(); i++) {
                if (finHidPalindroms.get(i).size() > maxLenght)
                    maxLenght = finHidPalindroms.get(i).size();
            }

            // для каждого палиндрома нужно узнать, можно ли вставить букву в середину
            int[] paste = new int[2];
            int numderOfFinal = finHidPalindroms.size() - 1;
            boolean symUnder = false;
            int includ = 0;
            for (int i = finHidPalindroms.size() - 1; i >= 0; i++) {
                if(finHidPalindroms.get(i).size() == maxLenght){
                    paste = getIndexes(finHidPalindroms.get(i));
                }
                if(paste[0] + 1 != paste[1]){
                    numderOfFinal = i;
                    symUnder = true;
                    includ = paste[0] + 1;
                    break;
                }
            }

            char under;
            int n = 0;
            if(symUnder) {
                under = textInChar[includ];
                n = 1;
            }
            int lenPalin = finHidPalindroms.get(numderOfFinal).size() * 2 + n;
            String excep = new String();
            excep = findSym(finHidPalindroms.get(numderOfFinal), symUnder, includ, textInChar, lenPalin);

            System.out.format("Длина набольшего палиндрома = %d", lenPalin);
            System.out.println(excep);

        }
    }
}
