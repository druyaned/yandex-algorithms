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

public class HW4BusesInVasyuki {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader);
        int start = readInt(reader);
        int finish = readInt(reader);
        int k = readInt(reader);
        Vertex[] vertices = new Vertex[n + 1];
        for (int i = 1; i <= n; i++) {
            vertices[i] = new Vertex(i);
        }
        for (int i = 1; i <= k; i++) {
            int v1 = readInt(reader);
            int t1 = readInt(reader);
            int v2 = readInt(reader);
            int t2 = readInt(reader);
            final Vertex v = vertices[v1];
            v.departures.add(t1);
            v.descendants.add(v2);
            v.arrivals.add(t2);
        }
        Destination[] destinations = dijkstra(n, k, start, vertices);
        if (destinations[finish] == null) {
            writer.write("-1\n");
        } else {
            writer.write(Integer.toString(destinations[finish].arrival));
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
    
    private static class HeapMin {
        private final int capacity;
        private int size;
        private final Destination[] arr;
        public HeapMin(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.arr = new Destination[capacity];
        }
        //-Modifiers--------------------------------------------------------------------------------
        public boolean add(Destination val) {
            if (size == capacity) {
                return false;
            }
            arr[size] = val;
            int i = size++;
            int a = (i - 1) / 2; // ancestor
            while (a >= 0 && arr[a].arrival > arr[i].arrival) {
                swap(a, i);
                i = a;
                a = (i - 1) / 2;
            }
            return true;
        }
        public Destination pop() {
            if (size == 0) {
                return null;
            }
            Destination root = arr[0];
            arr[0] = arr[--size];
            int i = 0;
            int l = 1, r = 2, d; // l, r - left and right descendants; d - min descendant
            while (l < size) {
                d = r < size && arr[r].arrival < arr[l].arrival ? r : l;
                if (arr[i].arrival > arr[d].arrival) {
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
            Destination toSwap = arr[i2];
            arr[i2] = arr[i1];
            arr[i1] = toSwap;
        }
    }
    
    private static class Vertex {
        public final int id;
        public final List<Integer> departures;
        public final List<Integer> descendants;
        public final List<Integer> arrivals;
        public Vertex(int id) {
            this.id = id;
            this.descendants = new ArrayList();
            this.departures = new ArrayList();
            this.arrivals = new ArrayList();
        }
    }
    
    private static class Destination {
        public final int vertex, arrival;
        public Destination(int vertex, int arrival) {
            this.vertex = vertex;
            this.arrival = arrival;
        }
    }
    
    private static Destination[] dijkstra(int n, int k, int start, Vertex[] vertices) {
        Destination[] destinations = new Destination[n + 1];
        boolean[] visited = new boolean[n + 1];
        HeapMin heap = new HeapMin(n + k);
        heap.add(new Destination(start, 0));
        while (heap.size != 0) {
            final Destination destination = heap.pop();
            if (!visited[destination.vertex]) {
                final Vertex v = vertices[destination.vertex];
                visited[v.id] = true;
                destinations[v.id] = destination;
                for (int i = 0; i < v.departures.size(); i++) {
                    final int descendant = v.descendants.get(i);
                    if (!visited[descendant] && destination.arrival <= v.departures.get(i)) {
                        heap.add(new Destination(descendant, v.arrivals.get(i)));
                    }
                }
            }
        }
        return destinations;
    }
    
}
