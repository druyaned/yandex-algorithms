package com.github.druyaned.yandexalgorithms.train3.l2dequesandheaps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5PiramidSort {
    
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
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int CAPACITY = (int)1e5;
        int n = readInt(reader);
        HeapMin heap = new HeapMin(CAPACITY);
        for (int i = 0; i < n; ++i) {
            heap.add(readInt(reader));
        }
        if (n > 0) {
            writer.write(Integer.toString(heap.pop()));
        }
        for (int i = 1; i < n; ++i) {
            writer.write(" " + heap.pop());
        }
        writer.write('\n');
    }
    
    private static class HeapMin {

        private final int capacity;
        private int size;
        private final int[] arr;

        public HeapMin(int capacity) {
            this.capacity = capacity;
            size = 0;
            arr = new int[capacity];
        }

        public int capacity() {
            return capacity;
        }

        public int size() {
            return size;
        }

        public int root() {
            return size == 0 ? 0 : arr[0];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean add(int val) {
            if (size == capacity) {
                return false;
            }
            arr[size] = val;
            for (int node = size++, ancestor = (node - 1) / 2;
                    ancestor >= 0 && arr[ancestor] > arr[node];
                    node = ancestor, ancestor = (node - 1) / 2) {
                int temp = arr[ancestor];
                arr[ancestor] = arr[node];
                arr[node] = temp;
            }
            return true;
        }

        public int pop() {
            if (size == 0) {
                return 0;
            }
            int root = arr[0];
            arr[0] = arr[--size];
            for (int node = 0, l = 2 * node + 1, r = 2 * node + 2;
                    l < size;
                    l = 2 * node + 1, r = 2 * node + 2) {
                int d = r < size && arr[r] < arr[l] ? r : l;
                if (arr[node] > arr[d]) {
                    int temp = arr[d];
                    arr[d] = arr[node];
                    arr[node] = temp;
                } else {
                    break;
                }
                node = d;
            }
            return root;
        }

    }
    
}
