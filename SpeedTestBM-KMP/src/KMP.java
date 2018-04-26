public class KMP {

    /** Префикс-функция для КМП **/
    public int[] PrefFunc(String xS){
        //инициализация массива-результата длиной строки х
        int[] result = new int[xS.length()];
        int i = 0;
        int j = -1;
        result[0] = -1;

        char[] x = xS.toCharArray();

        //вычисление префикс функции
        while(i < xS.length() - 1){
            while( (j >= 0) && (x[j] != x[i]))
                j = result[j];
            i++;
            j++;
            if(x[i] == x[j])
                result[i] = result[j];
            else
                result[i] = j;
        }
        return result;
    }

    /** Поиск включений хС в сС **/
    public void FindKMP(String xS, String sS){
        if(xS.length() > sS.length()){
            System.out.println("Конец");
            return;
        }

        int[] d = PrefFunc(xS);
        int i = 0;
        int j;
        char[] x = xS.toCharArray();
        char[] s = sS.toCharArray();
        while(i < sS.length()){
            for(j = 0; (i< sS.length()) && (j < xS.length()); i++, j++)
                while((j >= 0) && (x[j] != s[i]))
                    j = d[j];
            if(j == xS.length())
                System.out.println("Паттерн " + xS + " найден в позиции " + (i-j));
        }
    }
}
