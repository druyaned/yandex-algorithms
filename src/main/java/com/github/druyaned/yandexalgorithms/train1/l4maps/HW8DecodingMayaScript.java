package com.github.druyaned.yandexalgorithms.train1.l4maps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW8DecodingMayaScript {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static class LetterCount {
        
        private static final int N = 26 * 2; // a-zA-Z
        
        private final int[] counts = new int[N];
        
        public int get(char ch) {
            if ('a' <= ch && ch <= 'z') {
                return counts[ch - 'a'];
            } else if ('A' <= ch && ch <= 'Z') {
                return counts[26 + ch - 'A'];
            } else {
                return -1;
            }
        }
        
        public void increment(char ch) {
            if ('a' <= ch && ch <= 'z') {
                ++counts[ch - 'a'];
            } else if ('A' <= ch && ch <= 'Z') {
                ++counts[26 + ch - 'A'];
            }
        }
        
        public void decrement(char ch) {
            if ('a' <= ch && ch <= 'z') {
                --counts[ch - 'a'];
            } else if ('A' <= ch && ch <= 'Z') {
                --counts[26 + ch - 'A'];
            }
        }
        
        @Override
        public boolean equals(Object obj) {
            final LetterCount other = (LetterCount)obj;
            for (int i = 0; i < N; ++i) {
                if (counts[i] != other.counts[i]) {
                    return false;
                }
            }
            return true;
        }
        
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        LetterCount letterCount = new LetterCount();
        String[] elements = reader.readLine().split(" ");
        int wordLength = Integer.parseInt(elements[0]);
        int sequenceLength = Integer.parseInt(elements[1]);
        int chVal;
        while ((chVal = reader.read()) != '\n') {
            letterCount.increment((char)chVal);
        }
        LetterCount currentCount = new LetterCount();
        String sequence = reader.readLine();
        for (int i = 0; i < wordLength; ++i) {
            currentCount.increment(sequence.charAt(i));
        }
        int entryCount = letterCount.equals(currentCount) ? 1 : 0;
        for (int prevFstI = 0, lstI = wordLength; lstI < sequenceLength; ++prevFstI, ++lstI) {
            currentCount.decrement(sequence.charAt(prevFstI));
            currentCount.increment(sequence.charAt(lstI));
            if (letterCount.equals(currentCount)) {
                ++entryCount;
            }
        }
        writer.write(entryCount + "\n");
    }
    
}
