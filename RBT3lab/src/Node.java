public class Node {
    public String color;
    public String lastName;
    public String firstName;
    public String numberOfBook;
    public int[] marks = new int[5];
    public Node left = null;
    public Node right = null;
    public Node parent = null;

    public Node(){
        color = "red";
        lastName = "";
        firstName = "";
        numberOfBook = "";
    }

    public Node(String _color, String _lastName, String _firstName,
                String _numberOfBook, int[] _marks){
        color = _color;
        lastName = _lastName;
        firstName = _firstName;
        numberOfBook = _numberOfBook;
        for(int i = 0; i < 5; i++)
            marks[i] = _marks[i];
    }
}
