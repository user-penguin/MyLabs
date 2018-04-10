import java.util.ArrayList;

public class Record {
    public char sym;
    public ArrayList<Integer> nums;

    public Record(char _sym, int _num){
        sym = _sym;
        nums = new ArrayList<>();
        nums.add(_num);
    }
}
