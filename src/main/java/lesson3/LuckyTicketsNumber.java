package lesson3;

import java.math.BigInteger;

public class LuckyTicketsNumber {

    static final int N = 50;
    static final int MAX_SUM = N * 9;
    static BigInteger[][] arr = new BigInteger[N + 1][MAX_SUM + 1];

    public static void calc(int k, int n) {
        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < 10 && (k - i) >= 0; i ++) {
            BigInteger result = arr[n - 1] [k - i];
            if (result == null) {
                result = BigInteger.ZERO;
            }
            sum = sum.add(result);
        }
        arr[n][k] = sum;
    }

    public static void main(String[] args) {
        arr[0][0] = BigInteger.ONE;

        for (int n = 1; n <= N; n ++) {
            for (int k = 0; k <= 9 * n; k ++) {
                calc(k, n);
            }
        }

        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i <= MAX_SUM; i ++) {
            result = result.add(arr[N][i].multiply(arr[N][i]));
        }
        System.out.println(result);

    }

}
