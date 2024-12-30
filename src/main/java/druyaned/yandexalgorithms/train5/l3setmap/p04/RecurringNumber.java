package druyaned.yandexalgorithms.train5.l3setmap.p04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RecurringNumber {
    
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
        int k = readInt(reader);
        HashTable table = new HashTable();
        for (int i = 0; i < n; i++) {
            int value = readInt(reader);
            Pair pair = table.contains(value);
            if (pair != null) {
                if (i - pair.p <= k) {
                    writer.write("YES\n");
                    return;
                }
                pair.p = i;
            } else {
                table.add(value, i);
            }
        }
        writer.write("NO\n");
    }
    
    private static class Pair {
        private final int v; // value
        private int p; // last position
        public Pair(int value, int prev) {
            this.v = value;
            this.p = prev;
        }
    }
    
    private static class HashTable {
        public final int CAPACITY = 100019;
        private final List<List<Pair>> table = new ArrayList(CAPACITY);
        public HashTable() {
            for (int i = 0; i < CAPACITY; i++) {
                table.add(null);
            }
        }
        public Pair contains(int value) {
            int i = hash(value);
            if (table.get(i) == null) {
                return null;
            }
            int size = table.get(i).size();
            for (int j = 0; j < size; j++) {
                if (table.get(i).get(j).v == value) {
                    return table.get(i).get(j);
                }
            }
            return null;
        }
        public boolean add(int value, int lastPosition) {
            int i = hash(value);
            if (table.get(i) == null) {
                table.set(i, new ArrayList());
            }
            int size = table.get(i).size();
            if (size == CAPACITY) {
                return false;
            }
            for (int j = 0; j < size; j++) {
                if (table.get(i).get(j).v == value) {
                    return false;
                }
            }
            Pair pair = new Pair(value, lastPosition);
            table.get(i).add(pair);
            return true;
        }
        public Pair remove(int value) {
            int i = hash(value);
            if (table.get(i) == null) {
                return null;
            }
            int size = table.get(i).size();
            for (int j = 0; j < size; j++) {
                if (table.get(i).get(j).v == value) {
                    Pair removed = table.get(i).get(j);
                    table.get(i).set(j, table.get(i).get(size - 1));
                    table.remove(size - 1);
                    return removed;
                }
            }
            return null;
        }
        private int hash(int value) {
            return value < 0 ? -value % CAPACITY : value % CAPACITY;
        }
    }
    
    private static final char[] buf = new char[(int)2e6];
    
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
    
}
