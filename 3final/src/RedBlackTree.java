import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RedBlackTree {

    private final int RED = 0;
    private final int BLACK = 1;

    //непосредсственно узел
    private class Node {
        int number = -1;
        String first_name = "";
        String last_name = "";
        int[] marks = new int[5];
        int color = BLACK;
        Node left = nil;
        Node right = nil;
        Node parent = nil;


        //конструктор
        Node(int number, String first_name, String last_name, int[] marks) {
            this.number = number;
            this.first_name = first_name;
            this.last_name = last_name;
            for(int i = 0; i < 5; i++)
                this.marks[i] = marks[i];
        }
    }

    private final Node nil = new Node(-1, "", "", new int[5]);
    private Node root = nil;

    //вставка нового узла
    private void insert(Node node) {
        Node temp = root;
        if (root == nil) {
            root = node;
            node.color = BLACK;
            node.parent = nil;
        } else {
            node.color = RED;
            while (true) {
                if (node.number < temp.number) {
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.number >= temp.number) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            fixTree(node);
        }
    }

    //правка высоты
    private void fixTree(Node node) {
        while (node.parent.color == RED) {
            Node uncle = nil;
            if (node.parent == node.parent.parent.left) {
                uncle = node.parent.parent.right;

                if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.right) {
                    //Double rotation needed
                    node = node.parent;
                    rotateLeft(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateRight(node.parent.parent);
            } else {
                uncle = node.parent.parent.left;
                if (uncle != nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                }
                if (node == node.parent.left) {
                    //Double rotation needed
                    node = node.parent;
                    rotateRight(node);
                }
                node.parent.color = BLACK;
                node.parent.parent.color = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateLeft(node.parent.parent);
            }
        }
        root.color = BLACK;
    }

    //левый поворот
    void rotateLeft(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {//Need to rotate root
            Node right = root.right;
            root.right = right.left;
            right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = nil;
            root = right;
        }
    }

    //правый поворот
    void rotateRight(Node node) {
        if (node.parent != nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {//Need to rotate root
            Node left = root.left;
            root.left = root.left.right;
            left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = nil;
            root = left;
        }
    }


    public void printTree(Node node) {
        if (node == nil) {
            return;
        }
        printTree(node.left);
        System.out.print(((node.color==RED)?"Color: Red ":"Color: Black ")+"Key: "+node.number+" Parent: "+node.parent.number+"\n");
        printTree(node.right);

    }


    int createTree(String num){
        int kol = 0;
        try {
            FileInputStream fstream = new FileInputStream("/home/dmitry/" +
                    "Documents/development/MyLabs/MyLabs/resources/BRT" + num + ".txt");

            BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream));
            int count = Integer.parseInt(br1.readLine());
            kol = count;

            for(int i = 0; i < count; i++){
                String lastName = br1.readLine();
                String firstName = br1.readLine();
                int numberOfBook = Integer.parseInt(br1.readLine());
                int[] marks = new int[5];
                for(int j = 0; j < 5; j++)
                    marks[j] = Integer.parseInt(br1.readLine());
                Node novinka = new Node(numberOfBook, firstName, lastName, marks);
                insert(novinka);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return kol;
    }

    //поиск узла в дереве
    private Node findNode(Node findNode, Node node, RedBlackTree tree) {
        //если сразу упёрлись в нил
        if (tree.root == nil) {
            return null;
        }
        //для поиска по дереву
        if (findNode.number < node.number) {
            if (node.left != nil) {
                return findNode(findNode, node.left, tree);
            }
        } else if (findNode.number > node.number) {
            if (node.right != nil) {
                return findNode(findNode, node.right, tree);
            }
        } else if (findNode.number == node.number) {
            return node;
        }
        return null;
    }

    //удаление всего дерева
    void deleteTree(){
        root = nil;
    }

    void transplant(Node target, Node with){
        if(target.parent == nil){
            root = with;
        }else if(target == target.parent.left){
            target.parent.left = with;
        }else
            target.parent.right = with;
        with.parent = target.parent;
    }

    boolean delete(Node z){
        if((z = findNode(z, root, this))==null)return false;
        Node x;
        Node y = z; // у указывает на х
        //т.к цвет может измениться, нужно помнить о его начальном цвете
        int y_original_color = y.color;

        if(z.left == nil){
            x = z.right;
            transplant(z, z.right);
        }else if(z.right == nil){
            x = z.left;
            transplant(z, z.left);
        }else{
            y = treeMinimum(z.right);
            y_original_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if(y_original_color==BLACK)
            deleteFixup(x);
        return true;
    }

    void deleteFixup(Node x){
        while(x!=root && x.color == BLACK){
            if(x == x.parent.left){
                Node w = x.parent.right;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    rotateRight(w);
                    w = x.parent.right;
                }
                if(w.right.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            }else{
                Node w = x.parent.left;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    rotateLeft(w);
                    w = x.parent.left;
                }
                if(w.left.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK;
    }

    Node treeMinimum(Node subTreeRoot){
        while(subTreeRoot.left!=nil){
            subTreeRoot = subTreeRoot.left;
        }
        return subTreeRoot;
    }


    //обход дерева
    void travers(Node node1, Node root2, RedBlackTree rbt1, RedBlackTree rbt2){
        if(node1 == nil)
            return;
        if(rbt2.findNode(node1, root2, rbt2) != null){
            rbt2.delete(rbt2.findNode(node1, rbt2.root, rbt2));
            rbt1.delete(node1);
            travers(rbt1.root, rbt2.root, rbt1, rbt2);
            return;
        }
        if(node1.left != nil){
            travers(node1.left, root2, rbt1, rbt2);
        }
        if(node1.right != nil){
            travers(node1.right, root2, rbt1, rbt2);
        }
    }

    public static void ln(){
        System.out.println();
    }

    public static void main(String[] args) {
        RedBlackTree rbt1 = new RedBlackTree();
        RedBlackTree rbt2 = new RedBlackTree();
        int count1 = rbt1.createTree("1");
        int count2 = rbt2.createTree("2");

        System.out.println("Построение деревьев завершено");
        ln();
        System.out.println("Дерево один после построения");
        rbt1.printTree(rbt1.root);
        ln();
        System.out.println("Дерево два после построения");
        rbt2.printTree(rbt2.root);
        ln();
        ln();
        //начинаем обход по первому с удалением одинаковых узлов
        rbt1.travers(rbt1.root, rbt2.root, rbt1, rbt2);

        System.out.println("Построение деревьев завершено");
        ln();
        System.out.println("Дерево один после удаления одинаковых элементов");
        rbt1.printTree(rbt1.root);
        ln();
        System.out.println("Дерево два после удаления одинаковых элементов");
        rbt2.printTree(rbt2.root);
        ln();
        ln();
        System.out.println("Аццкей САТАНА отработал, встречайте баги, смертные!!!11!!!-_))0)))0))");

    }
}