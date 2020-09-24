package lesson1;

import java.math.BigInteger;

public class Fibonacci {

    public static void main(String[] args) {
        final int N = 1000;
        BigInteger first = new BigInteger("1");
        BigInteger second = new BigInteger("1");

        System.out.println(calculate_fibonacci(first, second, N));

    }

    public static BigInteger calculate_fibonacci(BigInteger first, BigInteger second, int number) {
        for (int i = 2; i <= number; i ++) {
            second = second.add(first);
            first = second.subtract(first);
        }
        return second;
    }
}
