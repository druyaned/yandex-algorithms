package com.github.druyaned.yandexalgorithms.train4.l3dijkstrasalgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class T1Dijkstra {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader); // vertex amount
        int k = readInt(reader); // edge amount
        Vertex[] vertices = new Vertex[n + 1];
        for (int i = 1; i <= n; i++) {
            vertices[i] = new Vertex(i);
        }
        for (int i = 1; i <= k; i++) {
            int v1 = readInt(reader);
            int v2 = readInt(reader);
            int w = readInt(reader);
            vertices[v1].descendants.add(v2);
            vertices[v1].weights.add(w);
        }
        int start = readInt(reader);
        int finish = readInt(reader);
        System.out.printf("start=%d finish=%d\n", start, finish);
        showVertices(n, vertices);
        Destination[] destinations = dijkstra(n, k, vertices, start);
        int[] path = getPath(n, start, finish, destinations);
        System.out.println();
        showDestinations(n, destinations);
        showPath(path, destinations);
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
    
    private static class HeapMin<T extends Comparable<T>> {
        private final int capacity;
        private int size;
        private final Object[] arr;
        public HeapMin(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.arr = new Object[capacity];
        }
        //-Modifiers--------------------------------------------------------------------------------
        public boolean add(T val) {
            if (size == capacity) {
                return false;
            }
            arr[size] = val;
            int i = size++;
            int a = (i - 1) / 2; // ancestor
            while (a >= 0 && ((T)arr[a]).compareTo((T)arr[i]) > 0) {
                swap(a, i);
                i = a;
                a = (i - 1) / 2;
            }
            return true;
        }
        public T pop() {
            if (size == 0) {
                return null;
            }
            T root = (T)arr[0];
            arr[0] = arr[--size];
            int i = 0;
            int l = 1, r = 2, d; // l, r - left and right descendants; d - min descendant
            while (l < size) {
                d = r < size && ((T)arr[r]).compareTo((T)arr[l]) < 0 ? r : l;
                if (((T)arr[i]).compareTo((T)arr[d]) > 0) {
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
        //-Methods----------------------------------------------------------------------------------
        public void show() {
            if (size == 0) {
                System.out.println("The heap is empty");
                return;
            }
            System.out.printf("heap: capacity=%d size=%d\n", capacity, size);
            int l = maxLengthOfElement();
            String element = "%" + l + "s";
            int p = power();
            int m = l / 2 + 1;
            int d = (l - 1) / 2;
            int nextLineCount = 2;
            System.out.print(" ".repeat((p-1) * m));
            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    System.out.printf(element + "\n", arr[i]);
                } else if (i == nextLineCount - 2) {
                    nextLineCount <<= 1;
                    System.out.printf(element + "\n", arr[i]);
                    p /= 2;
                    System.out.print(" ".repeat((p-1) * m));
                } else {
                    System.out.printf(element + "%s", arr[i], " ".repeat((2*p-1) * m - d));
                }
            }
        }
        //-Private-methods--------------------------------------------------------------------------
        private void swap(int i1, int i2) {
            Object toSwap = arr[i2];
            arr[i2] = arr[i1];
            arr[i1] = toSwap;
        }
        private int maxLengthOfElement() {
            int maxLength = 1;
            for (int i = 0; i < size; i++) {
                int lengthOfElement = arr[i].toString().length();
                if (maxLength < lengthOfElement) {
                    maxLength = lengthOfElement;
                }
            }
            return maxLength;
        }
        private int power() {
            int sizeCopy = size;
            int p = 1;
            while ((sizeCopy /= 2) > 0) {
                p *= 2;
            }
            return p;
        }
    }
    
    private static class Vertex {
        public final int id;
        public final List<Integer> descendants;
        public final List<Integer> weights;
        public Vertex(int id) {
            this.id = id;
            this.descendants = new ArrayList();
            this.weights = new ArrayList();
        }
    }
    
    private static class Destination implements Comparable<Destination> {
        public final int distance, vertex, previous;
        public Destination(int distance, int vertex, int previous) {
            this.distance = distance;
            this.vertex = vertex;
            this.previous = previous;
        }
        @Override public int compareTo(Destination other) {
            return distance - other.distance; // distance is always positive
        }
        @Override public String toString() {
            return Integer.toString(vertex) + "(" + distance + ")";
        }
    }
    
    private static Destination[] dijkstra(int n, int k, Vertex[] vertices, int start) {
        Destination[] destinations = new Destination[n + 1];
        boolean[] visited = new boolean[n + 1];
        HeapMin<Destination> heap = new HeapMin(n + k);
        heap.add(new Destination(0, start, -1));
        System.out.println();
        showVisited(n, visited);
        heap.show();
        while (heap.size != 0) {
            Destination destination = heap.pop();
            if (!visited[destination.vertex]) {
                Vertex v = vertices[destination.vertex];
                visited[v.id] = true;
                destinations[v.id] = destination;
                for (int i = 0; i < v.descendants.size(); i++) {
                    int descendant = v.descendants.get(i);
                    if (!visited[descendant]) {
                        int distance = destination.distance + v.weights.get(i);
                        heap.add(new Destination(distance, descendant, v.id));
                    }
                }
            }
            System.out.println();
            showVisited(n, visited);
            heap.show();
        }
        return destinations;
    }
    
    private static int[] getPath(int n, int start, int finish, Destination[] destinations) {
        if (destinations[finish] == null) {
            return null;
        }
        final Destination[] d = destinations;
        int l = 0;
        int[] reversePath = new int[n + 1];
        for (int node = finish; d[node] != null; node = d[node].previous) {
            reversePath[l++] = node;
            if (node == start) {
                break;
            }
        }
        int[] path = new int[l];
        for (int i = 0; i < l; i++) {
            path[i] = reversePath[l - i - 1];
        }
        return path;
    }
    
    private static void showVertices(int n, Vertex[] vertices) {
        System.out.println("vertices:");
        for (int i = 1; i <= n; i++) {
            System.out.printf("%d:", i);
            Vertex v = vertices[i];
            for (int j = 0; j < v.descendants.size(); j++) {
                System.out.printf(" %d(%d)", v.descendants.get(j), v.weights.get(j));
            }
            System.out.println();
        }
    }
    
    private static void showVisited(int n, boolean[] visited) {
        System.out.print("visited:");
        for (int i = 1; i <= n; ++i) {
            if (visited[i]) {
                System.out.printf(" %d", i);
            }
        }
        System.out.println();
    }
    
    private static void showDestinations(int n, Destination[] destinations) {
        final Destination[] d = destinations;
        System.out.println("destinations:");
        for (int i = 1; i <= n; i++) {
            if (d[i] == null) {
                System.out.printf("%d: null\n", i);
            } else {
                System.out.printf("%d: dist=%d prev=%d\n", i, d[i].distance, d[i].previous);
            }
        }
    }
    
    private static void showPath(int[] path, Destination[] destinations) {
        System.out.print("path:");
        if (path == null) {
            System.out.println(" null");
            return;
        }
        for (int i = 0; i < path.length; i++) {
            System.out.printf(" %d", path[i]);
        }
        System.out.printf("; dist=%d\n", destinations[path[path.length - 1]].distance);
    }
    
}
/*
intput:
9 11
1 2 4
1 3 2
1 8 1
2 5 1
2 7 4
3 5 1
4 1 5
5 6 1
6 7 2
7 4 2
8 5 2
1 4
output:
8
*/
