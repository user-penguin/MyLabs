package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main extends Application {

    //вычисление тангенса для вектора
    double tgns(double x1, double y1, double x2, double y2){
        return (y1 - y2)/(x1 - x2);
    }

    //грэхэм
    ArrayList<point> graham (ArrayList<point> pointsA){
        ArrayList<point> finalToRemove = new ArrayList<>();

        point left = new point(0, 0);
        left = pointsA.get(0);
        int left_index = 0;

        //поиск самой левой точки
        for(int i = 0; i < pointsA.size(); i++){
            if(pointsA.get(i).x <= left.x){
                left = pointsA.get(i);
                left_index = i;
            }
        }

        //теперь самая левая точка находится в начале нового массива
        finalToRemove.add(pointsA.get(left_index));


        //дерево для сортировки
        Tree sortTree = new Tree();

        //собственно формирование
        for(int i = 0; i < pointsA.size(); i++){
            if(i != left_index){
                double tg = tgns(pointsA.get(i).x, pointsA.get(i).y, left.x, left.y);
                sortTree.addingNodes(new Node(tg, i), sortTree.root);
            }
        }

        //теперь пульнём по левости вершины в список
        sortTree.sortArr(sortTree.root);

        for(int i = 0; i < sortTree.sort.size(); i++){
            finalToRemove.add(pointsA.get(sortTree.sort.get(i).i));
        }


        return finalToRemove;
    }

    boolean rotate(point first, point second, point third){
        double x0 = first.x, x1 = second.x, x2 = third.x;
        double y0 = first.y, y1 = second.y, y2 = third.y;

        //координаты векторов
        double ax = x1 - x0, ay = y0 - y1;
        double bx = x2 - x1, by = y1 - y2;

        if((ax*by - ay*bx) < 0)
            return false;
        else
            return true;
    }

    //формирование дерева


    void getPoints(ArrayList<point> points){
        try{
            FileInputStream fstream = new FileInputStream("/home/dmitry/" +
                    "Documents/development/MyLabs/MyLabs/resources/points.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String rd;
            int count = 0;
            count = Integer.parseInt(br.readLine());
            for(int i = 0; i < count; i++){
                point pare = new point(0,0);
                pare.x = Integer.parseInt(br.readLine());
                pare.y = Integer.parseInt(br.readLine());
                points.add(pare);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        ArrayList<point> points = new ArrayList<>();
        //getPoints(points);
        Random ranNum = new Random();
        for(int i = 0; i < 50; i++){
            points.add(new point(ranNum.nextInt(700) + 20, ranNum.nextInt(600) + 20));
        }


        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        Group root = new Group();
        primaryStage.setTitle("Grehem Algorythm");
        //выведем точки
        primaryStage.setScene(new Scene(root, 730, 730));

        Canvas canvas = new Canvas(1000, 800); // создаем новый объект Canvas с шириной 300px, и высотой 275px
        root.getChildren().add(canvas); // добавляем его в корневой контейнер
        GraphicsContext context = canvas.getGraphicsContext2D(); // и получаем GraphicContext



        // Button
        Button button = new Button("build");
        Label label = new Label("");


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(points.size() < 3) {
                    //затираем
                    context.setFill(Color.WHITE);
                    context.fillRect(0,0,900, 900);

                    for(int i = 0; i < points.size(); i++) {
                        context.setFill(Color.ROSYBROWN); // устанавливаем цвет
                        context.fillOval(points.get(i).x - 5, points.get(i).y - 5, 10, 10); // рисуем овал с левым верхним углом в точке (10;20) и высотой = ширине = 50px
                    }
                    
                    return;
                }
                //затираем
                context.setFill(Color.WHITE);
                context.fillRect(0,0,900, 900);

                for(int i = 0; i < points.size(); i++) {
                    context.setFill(Color.ROSYBROWN); // устанавливаем цвет
                    context.fillOval(points.get(i).x - 5, points.get(i).y - 5, 10, 10); // рисуем овал с левым верхним углом в точке (10;20) и высотой = ширине = 50px
                }

                ArrayList<point> firstStep = graham(points);
                firstStep.add(firstStep.get(0));



                ArrayList<point> stack = new ArrayList<>();
                stack.add(firstStep.get(0));
                stack.add(firstStep.get(1));
                //теперь в "стеке" лежат первые две точки МВО

                for(int i = 2; i < firstStep.size(); i++){
                    if(rotate(stack.get(stack.size() - 2), stack.get(stack.size() - 1), firstStep.get(i)))
                        stack.add(firstStep.get(i));
                    else{
                        stack.remove(stack.size() - 1);
                        i--;
                    }
                }

                double[] xl1 = new double[stack.size()];
                double[] yl1 = new double[stack.size()];
                for(int i = 0; i < stack.size(); i++){
                    xl1[i] = stack.get(i).x;
                    yl1[i] = stack.get(i).y;
                }

                //обводим границу
                context.strokePolyline(xl1, yl1, stack.size());

                for(int i = 0; i < points.size(); i++){
                    if(points.get(i).x == stack.get(stack.size() - 1).x &&
                            points.get(i).y == stack.get(stack.size() - 1).y ){
                        points.remove(i);
                        i = -1;
                        stack.remove(stack.size() - 1);
                    }
                }

                int a = points.size();

            }
        });

        root.getChildren().addAll(button,label);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
