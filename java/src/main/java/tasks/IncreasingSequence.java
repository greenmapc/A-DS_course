package tasks;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class IncreasingSequence {
    public static void main(String[] args) {
        int n = 10;
        int[] data = generateData(n);
        System.out.println(Arrays.toString(data));
        Integer[] res = findingSequence(data);
        System.out.println(Arrays.toString(res));
    }

    public static Integer[] findingSequence(int[] a) {
//      возрастающая подпоследовательность
        int n = a.length;
        int[] d = new int[n];
        int[] pos = new int[n];
        int[] prev = new int[n-1];
        int length = 0;

        pos[0] = -1;
        d[0] = Integer.MIN_VALUE;
        for (int i = 1; i < n; i++) {
            d[i] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < n-1; i++) {
            int j = binarySearch(d, a[i]);
            if (d[j-1] < a[i] && a[i] < d[j]) {
                d[j] = a[i];
                pos[j] = i;
                prev[i] = pos[j - 1];
                length = max(length, j);
            }
        }

        List<Integer> answer = new LinkedList<>();
        int p = pos[length];
        while (p != -1) {
            answer.add(a[p]);
            p = prev[p];
        }
        Collections.reverse(answer);
        if(a[n-1] >= answer.get(answer.size() -1)) {
            answer.add(a[n-1]);
        }
        return answer.toArray(new Integer[answer.size()]);
    }

    private static int binarySearch(int d[], int b) {
        int l = -1;
        int r = d.length;
        while (l < r - 1) {
            int m = (l + r) / 2;
            if (d[m] < b) {
                l = m;
            } else
                r = m;
        }
        return r;
    }


    private static int[] generateData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = new Random().nextInt(100);
        }
        return data;
    }

}
