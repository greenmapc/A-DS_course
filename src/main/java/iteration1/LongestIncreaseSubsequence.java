package iteration1;

import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LongestIncreaseSubsequence {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(3, 4, 9, 2, 5, 1);
//        List<Integer> list = Arrays.asList(4, 3, 2, 1);
        Result result = findLongestIncreaseSubseq(list);
        System.out.println(result.getAns());
        System.out.println(result.getPath());

    }

    public static int binarySearch(int[] a, Integer element) {
        int l = 0;
        int r = a.length - 1;
        while (r - l > 1) {
            int mid = (l + r) / 2;
            if (a[mid] > element) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return r;
    }

    public static Result findLongestIncreaseSubseq(List<Integer> arr) {
        int n = arr.size();

//        число, на которое оканчивается возрастающая подпоследовательность длины i
        int[] d = new int[n];
//        на элементе с каким индексом оканчивается оптимальная подпоследовательность длины i
        int[] p = new int[n];
//        индекс того элемента, который должен стоять перед a[i] в оптимальной подпоследовательности
        int[] pred = new int[n];
        for (int i = 0; i < n; i ++) {
            if (i == 0) {
                d[i] = Integer.MIN_VALUE;
            } else {
                d[i] = Integer.MAX_VALUE;
            }
            pred[i] = Integer.MAX_VALUE;
            p[i] = Integer.MAX_VALUE;
        }


        for (int i = 0; i < n; i ++) {
            int integer = arr.get(i);
            int j = binarySearch(d, integer);
            if (d[j - 1] < integer && integer < d[j]) {
                p[j] = i;
                d[j] = integer;
                pred[i] = p[j - 1];
            }
        }

        int ans = -1;
        int position = -1;
        for (int i = 0; i < n; i ++)
            if (d[i] != Integer.MAX_VALUE) {
                ans = i;
            }

        position = p[ans];
        List<Integer> path = new ArrayList<Integer>();
        while (position != Integer.MAX_VALUE) {
            path.add(position);
            position = pred[position];
        }

        Collections.reverse(path);

        return Result.builder()
                .ans(ans)
                .path(path)
                .build();
    }

    @Builder
    @Data
    private static class Result {
        private int ans;
        private List<Integer> path;
    }

}
