import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ZeidJakob {
    double getArray(ArrayList<ArrayList<Double>> firstly){
        double EPS = 0;
        try{
            FileInputStream fstream = new FileInputStream("/home/dmitry/Documents/" +
                    "development/MyLabs/MyLabs/resources/JakZeid.txt");
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
        }catch (IOException e){
            System.out.println("Ошибка");
        }
        return EPS;
    }

    //вычисляем норму || x(k + 1) - x(k) ||
    double norm(double[] XK, double[] XKP){
        double[] sub = new double[XK.length];
        for(int i = 0; i < XK.length; i++)
            sub[i] = Math.abs(XK[i] - XKP[i]);
        double max = -1.0;
        for(int i = 0; i < XK.length; i++)
            if(sub[i] > max)
                max = sub[i];
        return max;
    }

    //вычисление очередного приближения
    void Jacob_iteration(double[][] A, double[] B, double[] XK) {
        double[] XKP = new double[XK.length];
        for(int i = 0; i < XK.length; i++) {
            XKP[i] = XK[i];
            XK[i] = 0;
        }

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A.length; j++){
                if(i != j)
                    XK[i] -= (A[i][j] / A[i][i]) * XKP[j];
            }
            XK[i] += B[i] / A[i][i];
        }
    }


    void Jacobi(double[][] A, double[] B, double EPS){

        //первоначальное приближение
        double[] XK = new double[A.length];
        for(int i = 0; i < A.length; i++)
            XK[i] = 0.0;

        //получим вектор х(1) из х(0)
        double[] XKP = new double[A.length];

        int countOfIter = 0;
        do {
            System.arraycopy(XK, 0, XKP, 0, XK.length);
            Jacob_iteration(A, B, XK);
            countOfIter++;
        } while(norm(XK, XKP) > EPS);

        for(int i = 0; i < XK.length; i++)
            System.out.println(XK[i]);
        System.out.println(countOfIter);
    }

    void Seidel_iteration(double[][] A, double[] B, double[] XK){
        double[] XKP = new double[XK.length];
        for(int i = 0; i < XK.length; i++) {
            XKP[i] = XK[i];
            XK[i] = 0;
        }

        for(int i = 0; i < A.length; i++){
            for(int j = 0; j < A.length; j++){
                if(i != j)
                    XK[i] -= (A[i][j] / A[i][i]) * XKP[j];
            }
            XK[i] += B[i] / A[i][i];
            //сохранили новое значение, чтобы использовать его в следующей внешней итерацией
            XKP[i] = XK[i];
        }
    }

    void Seidel(double[][] A, double[] B, double EPS){
        //первоначальное приближение
        double[] XK = new double[A.length];
        for(int i = 0; i < A.length; i++)
            XK[i] = 0.0;

        //получим вектор х(1) из х(0)
        double[] XKP = new double[A.length];

        int countOfIter = 0;
        do {
            System.arraycopy(XK, 0, XKP, 0, XK.length);
            Seidel_iteration(A, B, XK);
            countOfIter++;
        } while(norm(XK, XKP) > EPS);

        for(int i = 0; i < XK.length; i++)
            System.out.println(XK[i]);
        System.out.println(countOfIter);
    }

    public static void main(String[] args){
        ZeidJakob mn = new ZeidJakob();
        double EPS = 0;

        //сюда считаем матрицу AB
        ArrayList<ArrayList<Double>> firstly = new ArrayList<>();
        EPS = mn.getArray(firstly);


        //массивы для удобства работы
        int m = firstly.size();
        double[][] matrixA = new double[m][m];
        double[] matrixB = new double[m];
        for(int i = 0; i < m; i++){
            for(int g = 0; g < m; g++)
                matrixA[i][g] = firstly.get(i).get(g);
        }
        for(int i = 0; i < m; i++)
            matrixB[i] = firstly.get(i).get(m);
        //матрицы созданы

        //для метода Якоби важно, чтобы диагональные кэфы А были ненулевые
        boolean jacob = true;
        for(int i = 0; i < m; i++){
            if(matrixA[i][i] == 0)
                jacob = false;
        }

        if(!jacob)
            System.out.println("Есть нулевые диагональные коэф-ты, исправьте и запустите заново");
        else {
            mn.Jacobi(matrixA, matrixB, EPS);
            mn.Seidel(matrixA, matrixB, EPS);
        }

    }
}
