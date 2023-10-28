package com.github.druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HW3MostFrequentWord {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int chVal;
        StringBuilder builder = new StringBuilder();
        HashMap<String, Integer> wordCount = new HashMap<>();
        int maxCount = 0;
        String theWord = "";
        while ((chVal = reader.read()) != -1) {
            char ch = (char)chVal;
            if (Character.isWhitespace(ch) && builder.length() != 0) {
                String word = builder.toString();
                builder.setLength(0);
                Integer count = wordCount.get(word);
                int newCount = count == null ? 1 : count + 1;
                if (maxCount < newCount) {
                    maxCount = newCount;
                    theWord = word;
                } else if (maxCount == newCount) {
                    if (theWord.compareTo(word) > 0) {
                        theWord = word;
                    }
                }
                wordCount.put(word, newCount);
            } else if (!Character.isWhitespace(ch)) {
                builder.append(ch);
            }
        }
        writer.write(theWord);
        writer.write('\n');
    }
    
}
