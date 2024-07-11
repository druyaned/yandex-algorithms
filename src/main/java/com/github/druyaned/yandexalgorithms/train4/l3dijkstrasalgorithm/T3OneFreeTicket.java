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

public class T3OneFreeTicket {
    
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
        int k = readInt(reader);
        Vertex[] vertices = new Vertex[n + 1];
        Vertex[] reverseVertices = new Vertex[n + 1];
        for (int i = 1; i <= n; i++) {
            vertices[i] = new Vertex(i);
            reverseVertices[i] = new Vertex(i);
        }
        Edge[] edges = new Edge[k + 1];
        for (int i = 1; i <= k; i++) {
            int v1 = readInt(reader);
            int v2 = readInt(reader);
            int weight = readInt(reader);
            edges[i] = new Edge(i, v1, v2, weight);
            vertices[v1].descendants.add(v2);
            vertices[v1].weights.add(weight);
            reverseVertices[v2].descendants.add(v1);
            reverseVertices[v2].weights.add(weight);
        }
        int start = readInt(reader);
        int finish = readInt(reader);
        System.out.printf("start=%d finish=%d\n", start, finish);
        showVertices(n, vertices, reverseVertices);
        Destination[] destinations = dijkstra(n, k, vertices, start);
        Destination[] reverseDestinations = dijkstra(n, k, reverseVertices, finish);
        showDestinations(n, destinations, reverseDestinations);
        showEdges(k, edges);
        showDistanceWithFreeEdge(n, k, start, finish, destinations, reverseDestinations, edges);
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
            size = 0;
            arr = new Object[capacity];
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
            checkDescendants();
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
        private void checkDescendants() {
            for (int i = 0; i < size; i++) {
                int l = 2 * i + 1, r = 2 * i + 2;
                T arrI = (T)arr[i];
                if (r < size && (arrI.compareTo((T)arr[l]) > 0 || arrI.compareTo((T)arr[r]) > 0)) {
                    throw new IllegalStateException(String.format("In HeapMin " +
                            "ancestor must be not greater than any descendant: " +
                            "%s[%d]: %s[%d] %s[%d]", arr[i], i, arr[l], l, arr[r], r));
                }
                if (l < size && arrI.compareTo((T)arr[l]) > 0) {
                    throw new IllegalStateException(String.format("In HeapMin " +
                            "ancestor must be not greater than any descendant: " +
                            "%s[%d]: %s[%d]", arr[i], i, arr[l], l));
                }
            }
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
    
    private static class Edge {
        public final int id, v1, v2, weight;
        public Edge(int id, int v1, int v2, int weight) {
            this.id = id;
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
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
        }
        return destinations;
    }
    
    private static void showVertices(int n, Vertex[] vertices, Vertex[] reverseVertices) {
        System.out.println("vertices:");
        for (int i = 1; i <= n; i++) {
            System.out.printf("%d:", i);
            for (int j = 0; j < vertices[i].descendants.size(); j++) {
                Vertex v = vertices[i];
                System.out.printf(" %d(%d)", v.descendants.get(j), v.weights.get(j));
            }
            System.out.println();
        }
        System.out.println("reverseVertices:");
        for (int i = 1; i <= n; i++) {
            System.out.printf("%d:", i);
            for (int j = 0; j < reverseVertices[i].descendants.size(); j++) {
                Vertex v = reverseVertices[i];
                System.out.printf(" %d(%d)", v.descendants.get(j), v.weights.get(j));
            }
            System.out.println();
        }
    }
    
    private static void showDestinations(int n,
            Destination[] destinations, Destination[] reverseDestinations) {
        final Destination[] d = destinations;
        final Destination[] rd = reverseDestinations;
        System.out.println("destinations:");
        for (int i = 1; i <= n; i++) {
            if (d[i] == null) {
                System.out.printf("%d: null\n", i);
            } else {
                System.out.printf("%d: dist=%d prev=%d\n", i, d[i].distance, d[i].previous);
            }
        }
        System.out.println("reverseDestinations:");
        for (int i = 1; i <= n; i++) {
            if (d[i] == null) {
                System.out.printf("%d: null\n", i);
            } else {
                System.out.printf("%d: dist=%d prev=%d\n", i, rd[i].distance, rd[i].previous);
            }
        }
    }
    
    private static void showEdges(int k, Edge[] edges) {
        final Edge[] e = edges;
        System.out.println("edges:");
        for (int i = 1; i <= k; i++) {
            System.out.printf("%d) [%d -> %d]=%d\n", i, e[i].v1, e[i].v2, e[i].weight);
        }
    }
    
    private static void showDistanceWithFreeEdge(int n, int k, int start, int finish,
            Destination[] destinations, Destination[] reverseDestinations, Edge[] edges) {
        Destination[] d = destinations;
        Destination[] rd = reverseDestinations;
        int distance = destinations[finish].distance;
        int edgeId = -1;
        for (int i = 1; i <= k; i++) {
            final Edge e = edges[i];
            int newDistance = d[e.v1].distance + rd[e.v2].distance;
            if (distance > newDistance) {
                distance = newDistance;
                edgeId = i;
            }
        }
        System.out.printf("distanceWithFreeEdge=%d edgeId=%d\n", distance, edgeId);
    }
    
}
/*
input:
9 11
1 2 4
1 3 2
1 8 1
2 5 0
2 7 4
3 5 1
4 1 5
5 6 1
6 7 2
7 4 2
8 5 2
1 6
output:
dist=1 edgeId=1
*/