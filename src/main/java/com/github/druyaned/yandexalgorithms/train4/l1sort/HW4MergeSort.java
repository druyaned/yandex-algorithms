package com.github.druyaned.yandexalgorithms.train4.l1sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4MergeSort {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static final char[] buf = new char[15];
    
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
    
    private static void show(int n, int[] arr) {
        System.out.print("\narr:");
        for (int i = 0; i < n; ++i) {
            System.out.printf(" %d", arr[i]);
        }
        System.out.println();
    }
    
    private static void show(IntDeque paramBegin, IntDeque paramEnd) {
        System.out.print("stack:");
        for (int i = 0; i < paramBegin.size; ++i) {
            System.out.printf(" [%d %d)", paramBegin.get(i), paramEnd.get(i));
        }
        System.out.println();
    }
    
    private static void swap(int[] arr, int i1, int i2) {
        int toSwap = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = toSwap;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        if (n == 0) {
            return;
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = readInt(reader);
        }
        int[] source = new int[n];
        System.arraycopy(arr, 0, source, 0, n);
        IntDeque paramBegin = new IntDeque(n);
        IntDeque paramEnd = new IntDeque(n);
        paramBegin.addFirst(0);
        paramEnd.addFirst(n);
        int nCopy = n;
        int power = 0;
        while ((nCopy >>= 1) > 0) {
            power++;
        }
        int unitCount = 1 << power;
        while (paramBegin.size != unitCount) {
            int begin = paramBegin.getFirst();
            int end = paramEnd.getFirst();
            paramBegin.removeFirst();
            paramEnd.removeFirst();
            int mid = (begin + end) / 2;
            paramBegin.addLast(begin);
            paramEnd.addLast(mid);
            paramBegin.addLast(mid);
            paramEnd.addLast(end);
        }
        show(n, arr);//show
        show(paramBegin, paramEnd);//show
        for (int i = 0; i < unitCount; ++i) {
            int begin = paramBegin.get(i);
            int end = paramEnd.get(i);
            if ((begin & 1) == (end & 1) && arr[begin] > arr[end - 1]) {
                swap(arr, begin, end - 1);
                swap(source, begin, end - 1);
            }
        }
        while (paramBegin.size != 0) {
            int b1 = paramBegin.getFirst();
            int e1 = paramEnd.getFirst();
            paramBegin.removeFirst();
            paramEnd.removeFirst();
            int b2 = paramBegin.getFirst();
            int e2 = paramEnd.getFirst();
            paramBegin.removeFirst();
            paramEnd.removeFirst();
            for (int i = b1, i1 = b1, i2 = b2; i < e2; ++i) {
                if (i1 < e1 && i2 < e2 && source[i1] <= source[i2] || i2 == e2) {
                    arr[i] = source[i1++];
                } else {
                    arr[i] = source[i2++];
                }
            }
            if (b1 != 0 || e2 != n) {
                paramBegin.addLast(b1);
                paramEnd.addLast(e2);
            }
            System.arraycopy(arr, b1, source, b1, e2 - b1);
            show(n, arr);//show
            show(paramBegin, paramEnd);//show
        }
        writer.write(Integer.toString(arr[0]));
        for (int i = 1; i < n; ++i) {
            writer.write(" " + arr[i]);
        }
        writer.write('\n');
    }
    
    private static class IntDeque {
        
        private final int capacity;
        private int size;
        private final int[] arr;
        private int head, tail;
        
        public IntDeque(int capacity) {
            this.capacity = capacity;
            size = 0;
            arr = new int[capacity];
            head = tail = 0;
        }
        
        public int get(int i) {
            return head <= tail ? arr[head + i] :
                    i < capacity - head ? arr[head + i] : arr[i - capacity + head];
        }
        
        public int getFirst() {
            return arr[head];
        }
        
        public int getLast() {
            return arr[tail];
        }
        
        public boolean addFirst(int val) {
            if (size == capacity) {
                return false;
            }
            if (size == 0) {
                arr[0] = val;
                size = 1;
                return true;
            }
            if (head == 0) {
                arr[head = capacity - 1] = val;
            } else {
                arr[--head] = val;
            }
            ++size;
            return true;
        }
        
        public boolean addLast(int val) {
            if (size == capacity) {
                return false;
            }
            if (size == 0) {
                arr[0] = val;
                size = 1;
                return true;
            }
            if (tail == capacity - 1) {
                arr[tail = 0] = val;
            } else {
                arr[++tail] = val;
            }
            ++size;
            return true;
        }
        
        public boolean removeFirst() {
            if (size == 0) {
                return false;
            }
            if (size == 1) {
                head = tail = 0;
                size = 0;
                return true;
            }
            if (head == capacity - 1) {
                head = 0;
            } else {
                ++head;
            }
            --size;
            return true;
        }
        
        public boolean removeLast() {
            if (size == 0) {
                return false;
            }
            if (size == 1) {
                head = tail = 0;
                size = 0;
                return true;
            }
            if (tail == 0) {
                tail = capacity - 1;
            } else {
                --tail;
            }
            --size;
            return true;
        }
        
    }
    
}
/*
input:
9
5 2 9 6 2 3 5 1 5
output:
1 2 2 3 5 5 5 6 9

n=13
stack: [0 13)
stack: [0 6) [6 13)
stack: [0 3) [3 6) [6 9) [9 13)
stack: [0 1) [1 3) [3 4) [4 6) [6 7) [7 9) [9 11) [11 13)
merge: [0 1) [1 3) [3 4) [4 6) [6 7) [7 9) [9 11) [11 13)
merge: [0 3) [3 6) [6 9) [9 13)
merge: [0 6) [6 13)
merge: [0 13)
*/