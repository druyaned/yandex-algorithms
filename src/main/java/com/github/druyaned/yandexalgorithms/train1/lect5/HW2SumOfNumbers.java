package com.github.druyaned.yandexalgorithms.train1.lect5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2SumOfNumbers {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elements = reader.readLine().split(" ");
        int n = Integer.parseInt(elements[0]);
        int k = Integer.parseInt(elements[1]);
        elements = reader.readLine().split(" ");
        int[] numbers = new int[n];
        for (int i = 0; i < n; ++i) {
            numbers[i] = Integer.parseInt(elements[i]);
        }
        // solve
        int curSum = 0;
        int count = 0;
        for (int l = 0, r = 0; l < n; ++l) {
            while (r < n && curSum < k) {
                curSum += numbers[r++];
            }
            if (curSum == k) {
                ++count;
            }
            curSum -= numbers[l];
        }
        writer.write(count + "\n");
    }
    
}
/*
Храню текущую сумму и ставлю 2 указателя на начало и конец интервала.
Буду расширять интервал, пока curSum < K.

10 13
3 1 8 4 9 4 5 8 2 3
----
  k: 13
arr: 3  1  8  4  9  4  5  8  2  3
ind: 0  1  2  3  4  5  6  7  8  9
sum: 3  4 12 16
dif:    1  9 13
dif:       8 12 21
dif:          4 13
dif:             9 13
dif:                4  9 17
dif:                   5 13
dif:                      8 10 13
dif:                         2  5
dif:                            3
*/