public class KMP {
    public static void main(String[] args) {
        String target = "bbc abcdab abcdabd abde";// 主串
        String mode = "abcdabd";// 模式串
        System.out.println(matchString(target, mode)); // KMP匹配字符串
    }

    //计算部分匹配表
    public static int[] matchTable(char[] c) {
        int length = c.length;
        int[] a = new int[length];
        int i, j;
        a[0] = -1;
        i = 0;
        for (j = 1; j < length; j++) {
            i = a[j - 1];
            while (i >= 0 && c[j] != c[i + 1]) {
                i = a[i];
            }
            if (c[j] == c[i + 1]) {
                a[j] = i + 1;
            } else {
                a[j] = -1;
            }
        }
        return a;
    }
    //字符串匹配
    public static int matchString(String target, String mode) {
        char[] s = target.toCharArray();
        char[] t = mode.toCharArray();
        int[] next = matchTable(t);
        int i = 0;
        int j = 0;
        while (i <= s.length - 1 && j <= t.length - 1) {
            if (j == -1 || s[i] == t[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j < t.length) {
            return -1;
        } else {
            return i - t.length; // 返回模式串在主串中的头下标
        }
    }
}
