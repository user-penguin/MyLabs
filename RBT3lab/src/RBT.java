import java.io.*;

public class RBT {

    public static Node Root1;
    public static Node gag = new Node("black", "", "", "", new int[5]);


    public static void RBInsert(Node R, Node z){
        Node y = gag;
        Node x = R;
        while(x != gag){
            y = x;
            if(compare(z, x) < 0)
                x = x.left;
            if(compare(z, x) > 0)
                x = x.right;
        }
        z.parent = y;
        if(y == gag)
            Root1 = z;
        else if(compare(z, y) < 0)
            y.left = z;
        else
            y.right = z;
        z.left = gag;
        z.right = gag;
        z.color = "red";
        RBInsertFixup(R, z);
    }

    public static void  RBInsertFixup(Node R, Node z){
        Node y = null;
        while (z.parent.color.compareTo("red") == 0){
            if(z.parent == z.parent.parent.left){
                y = z.parent.parent.right;
                if(y.color.compareTo("red") == 0){
                    z.parent.color = "black";
                    y.color = "black";
                    z.parent.parent.color = "red";
                    z = z.parent.parent;
                }
                else if(z == z.parent.right){
                    z = z.parent;
                    LeftRotate(R, z);
                }
                z.parent.color = "black";
                z.parent.parent.color = "red";
                RightRotate(R, z);
            }
            else{
                y = z.parent.parent.left;
                if(y.color.compareTo("red") == 0){
                    z.parent.color = "black";
                    y.color = "black";
                    z.parent.parent.color = "red";
                    z = z.parent.parent;
                }
                else if(z == z.parent.left){
                    z = z.parent;
                    RightRotate(R, z);
                }
                z.parent.color = "black";
                z.parent.parent.color = "red";
                LeftRotate(R, z);
            }
            Root1.color = "black";
        }
    }

    public static void LeftRotate(Node R, Node x){
        Node y = null;
        y = x.right;
        x.right = y.left;

        if(y.left != gag)
            y.left.parent = x;
        y.parent = x.parent;

        if(x.parent == gag)
            Root1 = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;
    }

    public static void RightRotate(Node R, Node x){
        Node y = null;
        y = x.left;
        x.left = y.right;

        if(y.right != gag)
            y.right.parent = x;
        y.parent = x.parent;

        if(x.parent == gag)
            Root1 = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.right = x;
        x.parent = y;
    }

    public static int compare(Node z, Node x){
        if(z.lastName.compareTo(x.lastName) < 0)
            return -1;
        if(z.lastName.compareTo(x.lastName) > 0)
            return 1;
        if(z.lastName.compareTo(x.lastName) == 0)
            if(z.firstName.compareTo(x.firstName) < 0)
                return -1;
            else if(z.firstName.compareTo(x.firstName) > 0)
                return 1;
            else
                return 0;
        return 0;
    }

    public static void createTree(Node Tree){
        try {
            FileInputStream fstream = new FileInputStream("/home/dmitry/" +
                    "Documents/development/MyLabs/MyLabs/resources/BRT1.txt");

            BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream));
            int count = Integer.parseInt(br1.readLine());


            for(int i = 0; i < count; i++){
                Node insert = new Node();
                insert.lastName = br1.readLine();
                insert.firstName = br1.readLine();
                insert.numberOfBook = br1.readLine();
                for(int j = 0; j < 5; j++)
                    insert.marks[j] = Integer.parseInt(br1.readLine());
                RBInsert(Root1, insert);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args){
        // создадим два дерева путём считывания данных из файлов
        // сортируем по фамиилии, потом по имени
        Root1 = gag;
        Node Tree1 = Root1;
        createTree(Tree1);

        System.out.println("Done");
    }
}
