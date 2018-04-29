import java.util.ArrayList;


import java.util.ArrayList;
import java.util.Arrays;

    /**10. Задан набор слов. Построить из них любую цепочку таким образом, чтобы символ в начале
     следующего совпадал с одним из символов в середине предыдущего (не первым и не
     последним)**/

    public class rec5 {

        static ArrayList<ArrayList<char[]>> glob;

        //понять, первое ли выполнение или нет
        public static boolean first(boolean[] flags){
            boolean p = true;
            for(int i = 0; i < flags.length; i++)
                if(flags[i])
                    p = false;
            return p;
        }


        //проверка на совпадение первого элемента на вхождение в предыдущее слово
        static boolean inclrec(char[] element, char sym, int i){
            if(sym == element[i])
                return true;
            else if(i + 2 == element.length)
                return false;
            else
                return inclrec(element, sym, i+1);
        }

        static void print(ArrayList<char[]> list){
            for(int i = 0; i < list.size(); i++){
                System.out.print(String.valueOf(list.get(i)) + "->");
            }
            System.out.println();
        }


        //собственно сам рекурсивный обход
        public static void recursion(char[][] words, boolean[] flags, int number, ArrayList<char[]> sequence){

            flags[number] = true;
            sequence.add(words[number]);
            print(sequence);

            //от текущего последнего слова проходим по флагам
            //смотрим, есть ли что свободное
            for(int i = 0; i < 10; i++){
                if(!flags[i]){
                    //если местечко есть, проверяем, норм условие или нет
                    if(inclrec(sequence.get(sequence.size() - 1), words[i][0], 1)){
                        recursion(words, flags, i, sequence);
                        flags[i] = false;
                        sequence.remove(sequence.size()-1);
                    }

                }
            }
            //не хватает какого-то условия выхода

        }

        public static void main(String[] args){

            //слова из которых будем составлять последовательности
            char[][] words = new char[10][];
            words[0] = "василий".toCharArray();
            words[1] = "дмитрий".toCharArray();
            words[2] = "амфибрахий".toCharArray();
            words[3] = "корабль".toCharArray();
            words[4] = "алексей".toCharArray();
            words[5] = "галина".toCharArray();
            words[6] = "женщина".toCharArray();
            words[7] = "егор".toCharArray();
            words[8] = "рекурсия".toCharArray();
            words[9] = "доска".toCharArray();

            //массив с посещёнными словами
            boolean[] flags = new boolean[10];

            //список, в который будем паковать последовательность
            ArrayList<char[]> sequence =  new ArrayList<>();
            //список со всеми последовательностями
            glob = new ArrayList<>();

            for(int number = 0; number < 10; number++) {
                recursion(words, flags, number, sequence);
                sequence.clear();
                for(int j = 0; j < 10; j++)
                    flags[j] = false;
            }
        }
    }
