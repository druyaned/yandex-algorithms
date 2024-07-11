package com.github.druyaned.yandexalgorithms.train3.l1stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5GreatRelocation {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[16];
    
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
    
    private static class City {
        public final int ind, cost;
        public City(int ind, int cost) {
            this.ind = ind;
            this.cost = cost;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] costs = new int[n];
        for (int i = 0; i < n; ++i) {
            costs[i] = readInt(reader);
        }
        City[] stack = new City[n];
        int size = 0;
        stack[size++] = new City(0, costs[0]);
        int[] dests = new int[n];
        for (int i = 1; i < n; ++i) {
            while (size > 0 && stack[size - 1].cost > costs[i]) {
                City city = stack[--size];
                dests[city.ind] = i;
            }
            stack[size++] = new City(i, costs[i]);
        }
        while (size > 0) {
            City city = stack[--size];
            dests[city.ind] = -1;
        }
        writer.write(Integer.toString(dests[0]));
        for (int i = 1; i < n; ++i) {
            writer.write(" " + dests[i]);
        }
        writer.write('\n');
    }
    
}
/*
n=10
ind: 0 1 2 3 4 5 6 7 8 9
arr: 1 2 3 2 1 4 2 5 3 1
ans: * 4 3 4 * 6 9 8 9 *
*/