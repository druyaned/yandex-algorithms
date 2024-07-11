// https://contest.yandex.ru/contest/59542/problems/
package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuickArraySearch {
    
    private static final char[] BUFFER = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = readInt(reader);
        }
        a = heapSort(a, n);
        int k = readInt(reader);
        {
            int leftKey = readInt(reader);
            int rightKey = readInt(reader);
            int il = rightBinsearch(a, leftKey);
            int ir = leftBinsearch(a, rightKey);
            writer.write(Integer.toString(ir - il - 1));
        }
        for (int i = 1; i < k; i++) {
            int leftKey = readInt(reader);
            int rightKey = readInt(reader);
            int il = rightBinsearch(a, leftKey);
            int ir = leftBinsearch(a, rightKey);
            writer.write(" " + (ir - il - 1));
        }
        writer.write('\n');
    }
    
    public static int leftBinsearch(int[] sortedArray, int key) {
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }
        int left = 0, right = sortedArray.length - 1, mid;
        while (left < right) {
            mid = (left + right) / 2; // (l r)=(0 1) => mid=0
            if (key < sortedArray[mid]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return key < sortedArray[left] ? left : sortedArray.length;
    }
    
    public static int rightBinsearch(int[] sortedArray, int key) {
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }
        int left = 0, right = sortedArray.length - 1, mid;
        while (left < right) {
            mid = (left + right + 1) / 2; // (l r)=(0 1) => mid=1
            if (sortedArray[mid] < key) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return sortedArray[right] < key ? right : -1;
    }
    
    private static int[] heapSort(int[] resource, int n) {
        int[] a = new int[n];
        IntHeapMin heap = new IntHeapMin(n);
        for (int i = 0; i < n; i++) {
            heap.add(resource[i]);
        }
        for (int i = 0; i < n; i++) {
            a[i] = heap.pop();
        }
        return a;
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(BUFFER, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        BUFFER[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            BUFFER[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}

class IntHeapMin {
    
    private final int capacity;
    private int size;
    private final int[] arr;
    
    public IntHeapMin(int capacity) {
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
        return arr[0];
    }
    
    public boolean add(int val) {
        if (size == capacity) {
            return false;
        }
        arr[size] = val;
        int i = size++;
        int a = (i - 1) / 2; // ancestor
        while (a >= 0 && arr[a] > arr[i]) {
            swap(a, i);
            i = a;
            a = (i - 1) / 2;
        }
        return true;
    }
    
    public int pop() {
        if (size == 0) {
            return 0;
        }
        int root = arr[0];
        arr[0] = arr[--size];
        int i = 0;
        int l = 1, r = 2, d; // l, r - left and right descendants; d - min descendant
        while (l < size) {
            d = r < size && arr[r] < arr[l] ? r : l;
            if (arr[i] > arr[d]) {
                swap(i, d);
            } else {
                break;
            }
            i = d;
            l = 2 * i + 1;
            r = 2 * i + 2;
        }
        return root;
    }
    
    public boolean isEmpty() {
        return size == 0;
    }
    
    private void swap(int i1, int i2) {
        int toSwap = arr[i2];
        arr[i2] = arr[i1];
        arr[i1] = toSwap;
    }
    
}
