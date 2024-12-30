package druyaned.yandexalgorithms.train3.l4dinamicprog2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4Cafe {
    
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
    
    private static void show(final int INF, int n, int overpay, int[] costs, int[][] sum) {
        System.out.print("OY='дни'; OX='использованные купоны'\n");
        for (int d = 1; d <= n; ++d) {
            System.out.printf("%2d:", d);
            for (int c = 0; c <= overpay; ++c) {
                if (sum[d][c] == INF) {
                    System.out.print(" INF");
                } else {
                    System.out.printf(" %3d", sum[d][c]);
                }
            }
            System.out.printf(" | %d\n", costs[d]);
        }
    }
    
    private static void show(int n, int overpay, int[][] prev) {
        for (int d = 1; d <= n; ++d) {
            System.out.printf("%2d:", d);
            for (int c = 0; c <= overpay; ++c) {
                System.out.printf(" %3d", prev[d][c]);
            }
            System.out.println();
        }
    }
    
    private static void show(int size, int[] path) {
        if (size > 0) {
            System.out.print(path[size - 1]);
        }
        for (int i = size - 1; i > 0; --i) {
            System.out.print(" " + path[i - 1]);
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int INF = -1;
        int n = readInt(reader);
        if (n == 0) {
            writer.write("0\n0 0\n");
            return;
        }
        int[] costs = new int[n + 1];
        int overpay = 0;
        for (int i = 1; i <= n; ++i) {
            costs[i] = readInt(reader);
            if (costs[i] > 100) {
                ++overpay;
            }
        }
        int[][] sum = new int[n + 1][overpay + 1];
        int[][] prev = new int[n + 1][overpay + 1];
        boolean[][] usedCoupon = new boolean[n + 1][overpay + 1];
        for (int d = 0; d <= n; ++d) {
            for (int c = 0; c <= overpay; ++c) {
                sum[d][c] = INF;
                prev[d][c] = INF;
            }
        }
        if (costs[1] > 100) {
            sum[1][1] = costs[1];
            prev[1][1] = 0;
        } else {
            sum[1][0] = costs[1];
            prev[1][0] = 0;
        }
        for (int d = 2; d <= n; ++d) {
            for (int c = 0; c + 1 <= overpay; ++c) {
                if (sum[d-1][c+1] != INF) { // use coupon
                    if (sum[d][c] == INF || sum[d][c] > sum[d-1][c+1]) {
                        sum[d][c] = sum[d-1][c+1];
                        prev[d][c] = c + 1;
                        usedCoupon[d][c] = true;
                    }
                }
                if (sum[d-1][c] != INF && costs[d] > 100) { // don't use coupon
                    sum[d][c+1] = sum[d-1][c] + costs[d];
                    prev[d][c+1] = c;
                } else if (sum[d-1][c] != INF) { // don't use coupon
                    if (sum[d][c] == INF || sum[d][c] > sum[d-1][c] + costs[d]) {
                        sum[d][c] = sum[d-1][c] + costs[d];
                        prev[d][c] = c;
                        usedCoupon[d][c] = false;
                    }
                }
            }
            if (sum[d-1][overpay] != INF && costs[d] <= 100) { // don't use coupon
                if (sum[d][overpay] == INF || sum[d][overpay] > sum[d-1][overpay] + costs[d]) {
                    sum[d][overpay] = sum[d-1][overpay] + costs[d];
                    prev[d][overpay] = overpay;
                    usedCoupon[d][overpay] = false;
                }
            }
        }
        int bestC = sum[n][0] == INF ? 1 : 0;
        for (int c = 1; c <= overpay; ++c) {
            if (sum[n][c] != INF && sum[n][bestC] >= sum[n][c]) {
                bestC = c;
            }
        }
        int[] path = new int[overpay + 1];
        int size = 0;
        for (int d = n, c = bestC; d > 1; c = prev[d][c], --d) {
            if (usedCoupon[d][c]) {
                path[size++] = d;
            }
        }
        writer.write(sum[n][bestC] + "\n");
        writer.write(bestC + " " + size + "\n");
        for (int i = size; i > 0; --i) {
            writer.write(path[i - 1] + "\n");
        }
        System.out.print("\nsum:\n");
        show(INF, n, overpay, costs, sum);
        System.out.print("\nprev:\n");
        show(n, overpay, prev);
        System.out.print("\npath:\n");
        show(size, path);
        System.out.printf("left=%d used=%d\n", bestC, size);
        System.out.printf("minSum=%d\n", sum[n][bestC]);
    }
    
}
/*
input:
5
35 40 101 59 63
output:
235
0 1
5

input:
4
130 155 35 40
output:
205
0 1
2

input:
8
130 155 35 40 101 59 120 50
output:
415
0 2
2 7

if (costs[i] > 100) ++coupons.
dp -> sum.
Какие параметры?
sum[day][coupons] - общая сумма;
day - день; coupons - количество имеющихся купонов.
Надо выяснить, как попасть в sum[day][coupons].
Как я решал эту задачу? Сначала написал кое как
работающую программу для простого примера и оформил
наглядный вывод для понимания происхожящего.
Потом написал вручную переходы для 1-го дня и для 2-го.
А потом copy-paste в циклы со сменой индексов на обобщенные.
*/
