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

public class HW2DijkstraWithPath {
    
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
        int start = readInt(reader);
        int finish = readInt(reader);
        Vertex[] vertices = new Vertex[n + 1];
        for (int i = 1; i <= n; i++) {
            vertices[i] = new Vertex(i);
        }
        int k = 0; // edge amount
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int weight = readInt(reader);
                if (i != j && weight != -1) {
                    k++;
                    vertices[i].descendants.add(j);
                    vertices[i].weights.add(weight);
                }
            }
        }
        Destination[] destinations = dijkstra(n, k, vertices, start);
        int[] path = getPath(n, start, finish, destinations);
        if (path != null && path.length != 0) {
            writer.write(Integer.toString(path[0]));
            for (int i = 1; i < path.length; i++) {
                writer.write(" " + path[i]);
            }
            writer.write('\n');
        } else {
            writer.write("-1\n");
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
        //-Private-methods--------------------------------------------------------------------------
        private void swap(int i1, int i2) {
            Object toSwap = arr[i2];
            arr[i2] = arr[i1];
            arr[i1] = toSwap;
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
    }
    
    private static Destination[] dijkstra(int n, int k, Vertex[] vertices, int start) {
        Destination[] destinations = new Destination[n + 1];
        boolean[] visited = new boolean[n + 1];
        HeapMin<Destination> heap = new HeapMin(n + k);
        heap.add(new Destination(0, start, -1));
        while (heap.size != 0) {
            Destination destination = heap.pop();
            if (!visited[destination.vertex]) {
                Vertex v = vertices[destination.vertex];
                visited[v.id] = true;
                destinations[v.id] = destination;
                for (int i = 0; i < v.descendants.size(); i++) {
                    int distance = destination.distance + v.weights.get(i);
                    heap.add(new Destination(distance, v.descendants.get(i), v.id));
                }
            }
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
    
}
/*
input:
8 1 8
 0  1  2  3  2 -1 -1 -1
-1  0 -1 -1 -1  2 -1 -1
-1 -1  0 -1 -1  3 -1 -1
-1 -1 -1  0 -1 -1 -1  1
-1 -1 -1 -1  0 -1  1 -1
-1 -1 -1 -1 -1  0 -1  2
-1 -1 -1 -1 -1 -1  0  2
-1 -1 -1 -1 -1 -1 -1  0
output:
4
*/