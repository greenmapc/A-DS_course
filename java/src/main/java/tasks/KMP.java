package tasks;

public class KMP {

    public static void kmpSearch(String str, String subStr) {

        int m = subStr.length();
        int n = str.length();

        int[] lps = new int[m];
        int j = 0;

        //Заполнение массива lps
        computeLPSArray(subStr, lps);
        int i = 0;
        while (i < n) {
            if (subStr.charAt(j) == str.charAt(i)) {
                i += 1;
                j += 1;
            }
            if (j == m) {
                System.out.println("Подстрока начинается с индекса  " + (i-j));
                j = lps[j-1];
            }
            else if(i < n && subStr.charAt(j) != str.charAt(i)) {
                if (j != 0) {
                    j = lps[j-1];
                }
                else {
                    i += 1;
                }
            }
        }


    }

    private static void computeLPSArray(String subString, int[] lps) {
        int i = 1;
        int length = 0;

        while (i < subString.length()) {
            if(subString.charAt(i) == subString.charAt(length)) {
                length += 1;
                lps[i] = length;
                i+=1;
            }
            else {
                if (length != 0) {
                    length = lps[length - 1];
                }
                else {
                    lps[i] = 0;
                    i += 1;
                }
            }
        }
    }



    public static void main(String[] args) {
        String str = "ABABDABACDABABCABAB";
        String pattern = "ABABCABAB";
        kmpSearch(str, pattern);

    }
}


