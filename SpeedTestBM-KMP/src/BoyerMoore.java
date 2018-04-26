import java.util.ArrayList;

public class BoyerMoore {

    /** управление поиском **/
    public void findPattern(String t, String p, int offs) {
        char[] text = t.toCharArray();
        char[] pattern = p.toCharArray();
        int pos = indexOf(text, pattern, offs);
        if (pos == -1) {
            firstWalk = true;
        }
        else {
            System.out.println("Паттерн " + p + " найден в позиции : " + pos);
            findPattern(t, p, pos + pattern.length);
        }
    }

    public boolean firstWalk = true;
    public int charTable[];
    public int offsetTable[];

    /** пробегаем по самой строке и сравниваем, что лучше применить **/
    public int indexOf(char[] text, char[] pattern, int offs) {
        if (pattern.length == 0)
            return 0;

        if(firstWalk) {
            charTable = makeCharTable(pattern);
            offsetTable = makeOffsetTable(pattern);
            firstWalk = false;
        }


        for (int i = pattern.length - 1 + offs, j; i < text.length;)
        {
            for (j = pattern.length - 1; pattern[j] == text[i]; --i, --j)
                if (j == 0)
                    return i;

            // i += pattern.length - j; // For naive method
            i += Math.max(offsetTable[pattern.length - 1 - j], charTable[text[i]]);
        }
        return -1;
    }


    /** Создаёт таблицу длин прыжков-переходов, требуемых для смещения
     при совпадении со стоп-символом или несовпадением **/
    private int[] makeCharTable(char[] pattern) {
        final int ALPHABET_SIZE = 256;
        int[] table = new int[ALPHABET_SIZE];
        for (int i = 0; i < table.length; ++i)
            table[i] = pattern.length;
        for (int i = 0; i < pattern.length - 1; ++i)
            table[pattern[i]] = pattern.length - 1 - i;
        return table;
    }


    /** Делает таблицу перехода на основе суффикса **/
    private static int[] makeOffsetTable(char[] pattern) {
        int[] table = new int[pattern.length];
        int lastPrefixPosition = pattern.length;
        for (int i = pattern.length - 1; i >= 0; --i)
        {
            if (isPrefix(pattern, i + 1))
                lastPrefixPosition = i + 1;
            table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
        }
        for (int i = 0; i < pattern.length - 1; ++i)
        {
            int slen = suffixLength(pattern, i);
            table[slen] = pattern.length - 1 - i + slen;
        }
        return table;
    }


    /** является ли слово префиксом шаблона **/
    private static boolean isPrefix(char[] pattern, int p) {
        for (int i = p, j = 0; i < pattern.length; ++i, ++j)
            if (pattern[i] != pattern[j])
                return false;
        return true;
    }

    /** возвращаем максимальную длину суффикса, входящего в оба слова **/
    private static int suffixLength(char[] pattern, int p) {
        int len = 0;
        for (int i = p, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j)
            len += 1;
        return len;
    }
}
