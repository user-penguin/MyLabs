package sample;

import java.util.ArrayList;

public class Tree {
    public Node root;

    Tree(){
        root = null;
    }

    public void addingNodes(Node node, Node to){
        if(this.root == null) {
            this.root = node;
            return;
        }
        if(node.tg > to.tg) {
            if (to.left == null) {
                node.parent = to;
                to.left = node;
            } else
                addingNodes(node, to.left);
        }
        else {
            if (to.right == null) {
                node.parent = to;
                to.right = node;
            } else
                addingNodes(node, to.right);
        }
    }

    public ArrayList<Node> sort = new ArrayList<>();

    //нужно вернуть отсортированный список точек по тангенсу
    public void sortArr(Node path){
        if(path.left != null)
            sortArr(path.left);
        sort.add(path);
        if(path.right != null)
            sortArr(path.right);
        return;
//
//        else {
//            sort.add(path);
//            if(path.right != null){
//                sortArr(path.right);
//            }
//            else
//                return;
//        }

    }
}
