package tasks.miller;

import java.util.Scanner;

public class MillerRabinTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        //количество проверок
        int k = 6;
        System.out.println(MillerRabin.isPrime(x, k));
    }
}
