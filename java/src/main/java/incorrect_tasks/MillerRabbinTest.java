/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package incorrect_tasks;

import java.math.BigInteger;
import java.util.Random;

// тест Миллера — Рабина на простоту числа
public class MillerRabbinTest {

    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");
    private static final Random random = new Random();

    // производится k раундов проверки числа n на простоту
    public static boolean millerRabbin(BigInteger n, int k) {

        // если n == 2 или n == 3 - эти числа простые, возвращаем true
        if (n.equals(TWO)   || n.equals(THREE)) {
            return true;
        }

        // если n < 2 или n четное - возвращаем false
        if (n.compareTo(TWO) < 0 || n.mod(TWO).equals(BigInteger.ZERO)) {
            return false;
        }

        // представим n − 1 в виде (2^s)*t, где t нечётно, это можно сделать последовательным делением n - 1 на 2
        BigInteger t = n.subtract(BigInteger.ONE);

        int s = 0;

        while (t.mod(TWO).equals(BigInteger.ZERO)) {
            t = t.divide(TWO);
            s += 1;
        }

        // повторить k раз
        for (int i = 0; i < k; i++) {
            // выберем случайное целое число a в отрезке [2, n − 2]
            BigInteger a = new BigInteger(n.toByteArray().length, random).add(TWO);

            // x ← a^t mod n, вычислим с помощью возведения в степень по модулю
            BigInteger x = binPow(a, t, n);

            // если x == 1 или x == n − 1, то перейти на следующую итерацию цикла
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;

            // повторить s − 1 раз
            for (int r = 1; r < s; r++)
            {
                // x ← x^2 mod n
                x = binPow(x, TWO, n);

                // если x == 1, то вернуть "составное"
                if (x.equals(BigInteger.ONE))
                    return false;

                // если x == n − 1, то перейти на следующую итерацию внешнего цикла
                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            if (!x.equals(n.subtract(BigInteger.ONE)))
                return false;
        }

        // вернуть "вероятно простое"
        return true;
    }

    public static BigInteger binPow(BigInteger a, BigInteger base, BigInteger modValue) {
        a = a.mod(modValue);
        if (base.equals(BigInteger.ZERO)) return BigInteger.ONE;
        else if (base.mod(TWO).equals(BigInteger.ZERO)) {
            return binPow((a.multiply(a)).mod(modValue), base.divide(TWO), modValue);
        }
        else return (a.multiply(binPow(a, base.subtract(BigInteger.ONE), modValue))).mod(modValue);
    }

    public static void main(String[] args) {
        // пример простого
        BigInteger simpleBigInteger = new BigInteger("359334085968622831041960188598043661065388726959079837");
        // пример непростого!
        BigInteger nonSimpleBigInteger = new BigInteger("359334085968622831041960188598043661065388726959079833");

        System.out.println(millerRabbin(simpleBigInteger, 10));
        System.out.println(millerRabbin(nonSimpleBigInteger, 10));
    }

}
