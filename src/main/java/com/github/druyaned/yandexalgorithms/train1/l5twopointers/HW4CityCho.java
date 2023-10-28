package com.github.druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4CityCho {
    
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
        int range = Integer.parseInt(elements[1]);
        elements = reader.readLine().split(" ");
        int[] dist = new int[n];
        for (int i = 0; i < n; ++i) {
            dist[i] = Integer.parseInt(elements[i]);
        }
        // solve
        long count = 0;
        for (int l = 0, r = 1; l < n; ++l) {
            while (r < n && (dist[r] - dist[l]) <= range) {
                ++r;
            }
            if (r == n) {
                break;
            }
            count += n - r;
        }
        writer.write(count + "\n");
    }
    
}
/*
n in [2; 3*10^5]
range in [1; 10^9]
dist[i] in [1; 10^9]
3*10^5 * 10^9 = 3*10^14 => use long

Нужно найти количество пар памятников : diff > range.
diff = dist[r] - dist[l]
Прохожу по l от начала до конца, перемещая r пока diff <= range.
К количеству буду прибавлять тех, что остались справа.
*/