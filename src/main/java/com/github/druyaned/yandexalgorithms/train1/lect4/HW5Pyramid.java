package com.github.druyaned.yandexalgorithms.train1.lect4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HW5Pyramid {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        HashMap<Integer, Integer> wh = new HashMap<>(n);
        for (int i = 0; i < n; ++i) {
            String[] words = reader.readLine().split(" ");
            Integer w = Integer.valueOf(words[0]);
            Integer h = Integer.valueOf(words[1]);
            Integer foundH = wh.get(w);
            if (foundH == null) {
                wh.put(w, h);
            } else if (h.compareTo(foundH) > 0) {
                wh.put(w, h);
            }
        }
        long maxH = 0;
        for (int h : wh.values()) {
            maxH += h;
        }
        writer.write(maxH + "\n");
    }
    
}
