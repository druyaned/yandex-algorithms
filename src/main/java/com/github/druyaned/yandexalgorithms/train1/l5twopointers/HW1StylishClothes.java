package com.github.druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1StylishClothes {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        // input
        int n = Integer.parseInt(reader.readLine());
        String[] elements = reader.readLine().split(" ");
        int[] shirts = new int[n];
        for (int i = 0; i < n; ++i) {
            shirts[i] = Integer.parseInt(elements[i]);
        }
        int m = Integer.parseInt(reader.readLine());
        elements = reader.readLine().split(" ");
        int[] pants = new int[m];
        for (int i = 0; i < m; ++i) {
            pants[i] = Integer.parseInt(elements[i]);
        }
        // solve
        int minDiff = shirts[0] > pants[0] ? shirts[0] - pants[0] : pants[0] - shirts[0];
        int minDiffShirt = shirts[0];
        int minDiffPants = pants[0];
        for (int i = 0, j = 0; i < n; ++i) {
            while (j < m && shirts[i] > pants[j]) {
                ++j;
            }
            if (j == m) {
                break;
            }
            int curDiff = pants[j] - shirts[i];
            if (minDiff > curDiff) {
                minDiff = curDiff;
                minDiffShirt = shirts[i];
                minDiffPants = pants[j];
            }
        }
        for (int j = 0, i = 0; j < m; ++j) {
            while (i < n && pants[j] > shirts[i]) {
                ++i;
            }
            if (i == n) {
                break;
            }
            int curDiff = shirts[i] - pants[j];
            if (minDiff > curDiff) {
                minDiff = curDiff;
                minDiffShirt = shirts[i];
                minDiffPants = pants[j];
            }
        }
        writer.write(minDiffShirt + " " + minDiffPants + "\n");
    }
    
}
/*
Прохожу 2 раза, сначала по майкам, потом по штанам.
Когда прохожу по майкам, то внутри цикла ишу штаны : shirts[i] <= pants[j].
Когда прохожу по штанам, то внутри цикла ишу майки : pants[j] <= shirts[i].
Так наверняка найду минимальную разность.

5
1 3 6 8 10
4
2 5 7 10
*/