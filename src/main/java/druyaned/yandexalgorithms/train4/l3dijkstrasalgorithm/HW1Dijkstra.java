package druyaned.yandexalgorithms.train4.l3dijkstrasalgorithm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HW1Dijkstra {
    
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
        int s = readInt(reader);
        int f = readInt(reader);
        int k = 0; // edge counter
        Object[] vertices = new Object[n + 1];
        for (int i = 1; i <= n; i++) {
            vertices[i] = new Vertex(i);
        }
        for (int v1 = 1; v1 <= n; v1++) {
            for (int v2 = 1; v2 <= n; v2++) {
                int weight = readInt(reader);
                if (v1 != v2) {
                    Vertex v = ((Vertex)vertices[v1]);
                    if (weight != -1) {
                        k++;
                        v.descendants.add(v2);
                        v.weights.add(weight);
                    }
                }
            }
        }
        showVertices(n, vertices); // TODO: debug
        boolean[] visited = new boolean[n + 1];
        int[] distances = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            distances[i] = -1;
        }
        HeapMin<Destination> heap = new HeapMin(n + k);
        heap.add(new Destination(0, s));
        while (heap.size != 0) {
            Destination d = heap.pop();
            if (!visited[d.vertex]) {
                visited[d.vertex] = true;
                distances[d.vertex] = d.distance;
                Vertex v = (Vertex)vertices[d.vertex];
                for (int i = 0; i < v.descendants.size(); i++) {
                    int distance = d.distance + v.weights.get(i);
                    heap.add(new Destination(distance, v.descendants.get(i)));
                }
            }
        }
        showDistances(n, distances); // TODO: debug
        writer.write(Integer.toString(distances[f]) + "\n");
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
        public final int distance;
        public final int vertex;
        public Destination(int distance, int vertex) {
            this.distance = distance;
            this.vertex = vertex;
        }
        @Override public int compareTo(Destination other) {
            return distance - other.distance; // distance is always positive
        }
    }
    
    private static class HeapMin<T extends Comparable<T>> {
        //-Fields-----------------------------------------------------------------------------------
        public final int capacity;
        private int size;
        private final Object[] arr;
        //-Constructors-----------------------------------------------------------------------------
        public HeapMin(int capacity) {
            this.capacity = capacity;
            size = 0;
            arr = new Object[capacity];
        }
        //-Getters----------------------------------------------------------------------------------
        public T root() {
            return (T)arr[0];
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
    
    private static void showVertices(int n, Object[] vertices) {
        System.out.println("vertices:");
        for (int i = 1; i <= n; i++) {
            Vertex v = (Vertex)vertices[i];
            System.out.printf("%d:", i);
            for (int j = 0; j < v.descendants.size(); j++) {
                System.out.printf(" %d(%d)", v.descendants.get(j), v.weights.get(j));
            }
            System.out.println();
        }
    }
    
    private static void showDistances(int n, int[] distances) {
        System.out.print("distances:");
        for (int i = 1; i <= n; i++) {
            System.out.printf(" %d", distances[i]);
        }
        System.out.println();
    }
    
}
