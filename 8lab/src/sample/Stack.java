package sample;

import java.util.ArrayList;

public class Stack {
    public ArrayList<point> data = new ArrayList<>();

    //положить в стек
    public void push(point node){
        data.add(node);
    }

    public point pop(){
        point last = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return last;
    }

    public point top(){
        return data.get(data.size() - 1);
    }
}
