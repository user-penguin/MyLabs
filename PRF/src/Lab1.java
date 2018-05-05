import java.util.Scanner;

public class Lab1 {
    static int notsg(int x){
        if (x > 0)return 0;
        else
            return 1;
    }


    static int d(int x, int i){
        //floor - округляет дробь в меньшую сторону.
        return (int) (((int) Math.floor(x / (int) Math.pow(16, i))) % 16) - 1;
    }

    static  boolean chark(int x, int z){
        if(x < Math.pow(16, z+1))
            return true;
        else
            return false;
    }

    static int log(int x){

        //return (int) Math.floor(Math.log(x) / Math.log(16)) + 1;
        int z = 0;
        while(z <= x && !chark(x, z)){
            z = z + 1;
        }
        return z + 1;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        int res = 0;
        for (int i = 0; i < log(x); i++)
            res = res + notsg(d(x, i));
        System.out.println(res);
    }
}
