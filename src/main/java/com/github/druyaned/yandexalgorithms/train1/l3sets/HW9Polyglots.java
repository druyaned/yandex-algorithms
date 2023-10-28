package com.github.druyaned.yandexalgorithms.train1.l3sets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class HW9Polyglots {
    
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
        HashMap<String, Integer> languages = new HashMap<>();
        HashSet<String> allKnows = new HashSet<>();
        int m;
        for (int i = 0; i < n; ++i) {
            m = Integer.parseInt(reader.readLine());
            for (int j = 0; j < m; ++j) {
                String language = reader.readLine();
                Integer count = languages.get(language);
                if (count == null) {
                    languages.put(language, 1);
                } else {
                    languages.put(language, count + 1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : languages.entrySet()) {
            String language = entry.getKey();
            int count = entry.getValue();
            if (count == n) {
                allKnows.add(language);
            }
        }
        writer.write(Integer.toString(allKnows.size()) + "\n");
        for (String language : allKnows) {
            writer.write(language + "\n");
        }
        writer.write(Integer.toString(languages.size()) + "\n");
        for (String language : languages.keySet()) {
            writer.write(language + "\n");
        }
    }
    
}
