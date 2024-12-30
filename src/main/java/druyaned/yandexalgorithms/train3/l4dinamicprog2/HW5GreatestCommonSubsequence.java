package druyaned.yandexalgorithms.train3.l4dinamicprog2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5GreatestCommonSubsequence {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
    private static int readInt(Reader reader) throws IOException {
        int c;
        while ((c = reader.read()) != -1 && c != '+' && c != '-' && !('0' <= c && c <= '9')) {}
        if (c == -1) {
            throw new NumberFormatException("empty input");
        }
        buf[0] = (char)c;
        int l = 1;
        while ((c = reader.read()) != -1 && '0' <= c && c <= '9') {
            buf[l++] = (char)c;
        }
        return Integer.parseInt(new String(buf, 0, l));
    }
    
    private static String asString(int n, int[] a) {
        StringBuilder builder = new StringBuilder(n * 2);
        if (n > 1) {
            builder.append(a[1]);
        }
        for (int i = 2; i <= n; ++i) {
            builder.append(' ').append(a[i]);
        }
        return builder.toString();
    }
    
    private static String asString(int n1, int n2, int[][] dp) {
        StringBuilder builder = new StringBuilder(2 * n1 * (n2 + 1));
        for (int i1 = 1; i1 <= n1; ++i1) {
            if (n2 > 1) {
                builder.append(dp[i1][1]);
            }
            for (int i2 = 2; i2 <= n2; ++i2) {
                builder.append(' ').append(dp[i1][i2]);
            }
            builder.append('\n');
        }
        return builder.toString();
    }
    
    private static String asString(int[] path, int size) {
        StringBuilder builder = new StringBuilder(size * 2);
        if (size > 0) {
            builder.append(path[size - 1]);
        }
        for (int i = size - 1; i > 0; --i) {
            builder.append(' ').append(path[i - 1]);
        }
        return builder.toString();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n1 = readInt(reader);
        int[] a1 = new int[n1 + 1];
        for (int i = 1; i <= n1; ++i) {
            a1[i] = readInt(reader);
        }
        int n2 = readInt(reader);
        int[] a2 = new int[n2 + 1];
        for (int i = 1; i <= n2; ++i) {
            a2[i] = readInt(reader);
        }
        int[][] dp = new int[n1 + 1][n2 + 1];
        for (int i1 = 1; i1 <= n1; ++i1) {
            for (int i2 = 1; i2 <= n2; ++i2) {
                if (a1[i1] == a2[i2]) {
                    dp[i1][i2] = dp[i1-1][i2-1] + 1;
                } else if (dp[i1-1][i2] > dp[i1][i2-1]) {
                    dp[i1][i2] = dp[i1-1][i2];
                } else {
                    dp[i1][i2] = dp[i1][i2-1];
                }
            }
        }
        int[] path = new int[n1 + n2];
        int size = 0;
        for (int i1 = n1, i2 = n2; dp[i1][i2] > 0; ) {
            if (a1[i1] == a2[i2]) {
                path[size++] = a1[i1];
                --i1;
                --i2;
            } else if (dp[i1-1][i2] > dp[i1][i2-1]) {
                --i1;
            } else {
                --i2;
            }
        }
        if (size > 0) {
            writer.write(Integer.toString(path[size - 1]));
        }
        for (int i = size - 1; i > 0; --i) {
            writer.write(" " + path[i - 1]);
        }
        writer.write('\n');
        System.out.printf("a1: %s\n", asString(n1, a1));
        System.out.printf("a2: %s\n", asString(n2, a2));
        System.out.printf("\ndp:\n%s\n", asString(n1, n2, dp));
        System.out.printf("path: %s\n", asString(path, size));
    }
    
}
/*
input:
4
9 2 4 1
5
6 2 9 4 5
output:
2 4

n1=4 n2=5
a1: 9 2 4 1
a2: 6 2 9 4 5
ans: 2 4

ind: 0 1 2 3 4
 a1: 9 2 4 1
 a2: 6 2 9 4 5
 s2: 2[1] 4[3] 5[4] 6[0] 9[2]
 b1: 2 1 3 -1

Можно отсортировать 2-й массив с сохранением изначальных индексов.
Потом с помощью бинпоиска искать текущий элемент 1-й последовательности
во 2-й. Задача сводиться к тому, чтобы найти в упорядоченном массиве индексов
наибольшую возрастающую последовательность. Сложность получается O(n*log(n)).

Но тут можно решить и за O(n^2) потому, что n <= 1000.
А значит можно решить с помощью динамического программирования с 2-мя
параметрами.
dp[i1][i2] - длина общей подпоследовательности для i1 и i2.
Как попасть в dp[i1][i2]? Если a1[i1] == a2[i2],
то dp[i1][i2] = dp[i1-1][i2-1] + 1. Если a1[i1] != a2[i2],
то dp[i1][i2] = max(dp[i1-1][i2], dp[i1][i2-1]).

n1=4 n2=5
ind: 1 2 3 4 5
a1:  9 2 4 1
a2:  6 2 9 4 5
dp:
a2 | 6 2 9 4 5 |
ind| 1 2 3 4 5 | a1
---+-----------+---
  1| 0 0 1 1 1 | 9
  2| 0 1 1 1 1 | 2
  3| 0 1 1 2 2 | 4
  4| 0 1 1 2 2 | 1
*/
