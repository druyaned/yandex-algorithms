package com.github.druyaned.yandexalgorithms.train3.l1stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3PostfixNotation {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int CAPACITY = (int)2e6;
        int[] stack = new int[CAPACITY];
        int size = 0;
        int c;
        while ((c = reader.read()) != -1) {
            if ('0' <= c && c <= '9') {
                stack[size++] = c - '0';
            }
            if (c == '+') {
                int b = stack[--size];
                int a = stack[--size];
                stack[size++] = a + b;
            }
            if (c == '-') {
                int b = stack[--size];
                int a = stack[--size];
                stack[size++] = a - b;
            }
            if (c == '*') {
                int b = stack[--size];
                int a = stack[--size];
                stack[size++] = a * b;
            }
        }
        writer.write(stack[0] + "\n");
    }
    
}
