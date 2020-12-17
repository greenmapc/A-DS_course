package tasks.miller;

public class MillerRabin {

    //n - исследуемое число
    //k - уровень точности
    public static boolean isPrime(int n, int k) {
        if (n < 1) {
            throw new IllegalArgumentException("число должно быть больше 1!");
        }
        //число <=3 - простое
        if (n <= 3) {
            return true;
        }
        //четное число !=2 не простое
        if (n % 2 == 0) {
            return false;
        }
        //берем нечетное число 13 -> 12 -> 6 -> 3
        int d = n - 1;
        while (d % 2 == 0)
            d /= 2;


        for (int i = 0; i < k; i++)
            if (!millerTest(d, n))
                return false;

        return true;
    }

    private static boolean millerTest(int d, int n) {

        // a - рандомное число от 2
        int a = 2 + (int) (Math.random() % (n - 4));

//        Вычислить (a ^ d) mod n
        int x = power(a, d, n);
        //соответсвтие 1 условия миллера-рабина
        if (x == 1 || x == n - 1)
            return true;


        while (d != n - 1) {

            //увеличиваем степень
            d *= 2;
            x = (x * x) % n;
            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }
        return false;
    }

    // а - рандомное число
    // n - проверяемое число
    private static int power(int a, int d, int n) {
        int res = 1;

        a = a % n;
        while (d > 0) {
            if ((d & 1) == 1)
                res = (res * a) % n;
            d = d >> 1; // y = y/2
            a = (a * a) % n;
        }

        return res;
    }
}
