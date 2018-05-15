import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class partialProblem {
    static double V = 0;
    double getArray(ArrayList<ArrayList<Double>> firstly){
        double Vreed = 0;
        double EPS = 0;
        try{
            FileInputStream fstream = new FileInputStream("D:/Study/MyLabs/" +
                    "resources/partialProblem.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            int size = 0;
            size = Integer.parseInt(br.readLine());
            for(int i = 0; i < size; i++){
                firstly.add(new ArrayList<Double>());
                String read = br.readLine();
                String[] toSplit = read.split(" ");
                for(int j = 0; j < size + 1; j++)
                    firstly.get(i).add(Double.parseDouble(toSplit[j]));
            }
            EPS = Double.parseDouble(br.readLine());
            V = Double.parseDouble(br.readLine());
        }catch (IOException e){
            System.out.println("Ошибка");
        }
        return EPS;
    }

    public static void main(String[] args) {
        partialProblem mn = new partialProblem();
        double EPS = 0;

        //сюда считаем матрицу AB
        ArrayList<ArrayList<Double>> firstly = new ArrayList<>();
        EPS = mn.getArray(firstly);
    }
}


