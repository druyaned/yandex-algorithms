package com.github.druyaned.yandexalgorithms.train2.divb.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4BenchInAtrium {
    
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
        String[] elems = reader.readLine().split(" ");
        int l = Integer.parseInt(elems[0]);
        int n = Integer.parseInt(elems[1]);
        boolean[] positions = new boolean[l];
        elems = reader.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            int pos = Integer.parseInt(elems[i]);
            positions[pos] = true;
        }
        // output
        if (l % 2 == 1 && positions[l / 2]) {
            writer.write((l / 2) + "\n");
            return;
        }
        int i1 = l / 2 - 1;
        int i2 = (l - 1) / 2 + 1;
        while (i1 >= 0 && !positions[i1]) {
            --i1;
        }
        while (i2 < l && !positions[i2]) {
            ++i2;
        }
        writer.write(i1 + " " + i2 + "\n");
    }
    
}
