package com.github.druyaned.yandexalgorithms.train3.l6breadthfirstsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HW2ShortestPath {
    
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
    
    private static void show(int n, List<List<Integer>> graph) {
        System.out.print("\ngraph:\n");
        for (int v = 1; v <= n; ++v) {
            System.out.printf("%2d:", v);
            for (int subV : graph.get(v)) {
                System.out.printf(" %d", subV);
            }
            System.out.println();
        }
    }
    
    private static void show(int n, int[] dist) {
        System.out.print("\ndist:");
        for (int i = 1; i <= n; ++i) {
            System.out.printf(" [%d]%d", i, dist[i]);
        }
        System.out.println();
    }
    
    private static void show(int[] path, int pathSize) {
        System.out.printf("\npath:");
        for (int i = pathSize; i > 0; --i) {
            System.out.printf(" %d", path[i - 1]);
        }
        System.out.println();
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        List<List<Integer>> graph = new ArrayList(n + 1);
        for (int i = 0; i <= n; ++i) {
            graph.add(new ArrayList());
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                if (readInt(reader) == 1) {
                    graph.get(i).add(j);
                }
            }
        }
        int v1 = readInt(reader);
        int v2 = readInt(reader);
        if (v1 == v2) {
            writer.write("0\n");
            return;
        }
        show(n, graph); // TODO: debug
        System.out.printf("v1=%d v2=%d\n", v1, v2); // TODO: debug
        int[] dist = new int[n + 1];
        int[] prev = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            dist[i] = -1;
        }
        Deque vertexDeque = new Deque(n * n);
        vertexDeque.addFirst(v1);
        dist[v1] = 0;
        while (vertexDeque.size > 0) {
            int v = vertexDeque.getFirst();
            vertexDeque.removeFirst();
            int newDist = dist[v] + 1;
            for (int subV : graph.get(v)) {
                if (dist[subV] == -1) {
                    vertexDeque.addLast(subV);
                    dist[subV] = newDist;
                    prev[subV] = v;
                    if (subV == v2) {
                        vertexDeque.clear();
                    }
                }
            }
        }
        show(n, dist); // TODO: debug
        writer.write(dist[v2] + "\n");
        if (dist[v2] != -1) {
            int[] path = new int[n];
            int pathSize = 0;
            for (int v = v2; v != v1; v = prev[v]) {
                path[pathSize++] = v;
            }
            path[pathSize++] = v1;
            show(path, pathSize); // TODO: debug
            writer.write(Integer.toString(path[pathSize - 1]));
            for (int i = pathSize - 1; i > 0; --i) {
                writer.write(" " + path[i - 1]);
            }
            writer.write('\n');
        }
    }
    
    private static class Deque {

        private final int capacity;
        private int size;
        private final int[] arr;
        private int head, tail;

        public Deque(int capacity) {
            this.capacity = capacity;
            size = 0;
            arr = new int[capacity];
            head = tail = 0;
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
        
        public void clear() {
            size = head = tail = 0;
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
1: 6
2: 3 7 8
3: 2
4: 6 8
5: 7
6: 1 4 7
7: 2 5 6
8: 2 4
9:

 | 1 2 3 4 5 6 7 8 9
-+------------------
1| 0 0 0 0 0 1 0 0 0
2| 0 0 1 0 0 0 1 1 0
3| 0 1 0 0 0 0 0 0 0
4| 0 0 0 0 0 1 0 1 0
5| 0 0 0 0 0 0 1 0 0
6| 1 0 0 1 0 0 1 0 0
7| 0 1 0 0 1 1 0 0 0
8| 0 1 0 1 0 0 0 0 0
9| 0 0 0 0 0 0 0 0 0

input:
9
0 0 0 0 0 1 0 0 0
0 0 1 0 0 0 1 1 0
0 1 0 0 0 0 0 0 0
0 0 0 0 0 1 0 1 0
0 0 0 0 0 0 1 0 0
1 0 0 1 0 0 1 0 0
0 1 0 0 1 1 0 0 0
0 1 0 1 0 0 0 0 0
0 0 0 0 0 0 0 0 0
1 3
output:
4
*/