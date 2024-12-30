package druyaned.yandexalgorithms.train5.l3setmap.p05;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class TwoFromThree {
    
    private static final char[] buf = new char[(int)2e6];
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        HashTable table1 = new HashTable();
        HashTable table2 = new HashTable();
        HashTable table3 = new HashTable();
        int n1 = readInt(reader);
        for (int i = 0; i < n1; i++) {
            table1.add(readInt(reader));
        }
        int n2 = readInt(reader);
        for (int i = 0; i < n2; i++) {
            table2.add(readInt(reader));
        }
        int n3 = readInt(reader);
        for (int i = 0; i < n3; i++) {
            table3.add(readInt(reader));
        }
        HashTable inTwoTable = new HashTable(); // 1 2; 1 3; 2 3
        table1.forEach(valueObj -> {
            if (table2.contains(valueObj) || table3.contains(valueObj)) {
                inTwoTable.add(valueObj);
            }
        });
        table2.forEach(valueObj -> {
            if (table3.contains(valueObj)) {
                inTwoTable.add(valueObj);
            }
        });
        List<Integer> inTwoList = new ArrayList(inTwoTable.size());
        inTwoTable.forEach(inTwoList::add);
        inTwoList.sort((v1, v2) -> v1.compareTo(v2));
        Iterator<Integer> iter = inTwoList.iterator();
        if (iter.hasNext()) {
            writer.write(iter.next().toString());
        }
        while (iter.hasNext()) {
            writer.write(" " + iter.next().toString());
        }
        writer.write('\n');
    }
    
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

class HashTable {
    
    public static final int CAPACITY = 20071;
    
    private final List<List<Integer>> table = new ArrayList(CAPACITY);
    private int size = 0;
    
    public HashTable() {
        for (int i = 0; i < CAPACITY; i++) {
            table.add(null);
        }
    }
    
    public boolean add(Integer valueObj) {
        int i = hash(valueObj);
        if (table.get(i) == null) {
            table.set(i, new ArrayList());
        }
        final int SIZE = table.get(i).size();
        for (int j = 0; j < SIZE; j++) {
            if (table.get(i).get(j).equals(valueObj)) {
                return false;
            }
        }
        table.get(i).add(valueObj);
        size++;
        return true;
    }
    
    public boolean contains(Integer valueObj) {
        int i = hash(valueObj);
        if (table.get(i) == null) {
            return false;
        }
        final int SIZE = table.get(i).size();
        for (int j = 0; j < SIZE; j++) {
            if (table.get(i).get(j).equals(valueObj)) {
                return true;
            }
        }
        return false;
    }
    
    public void forEach(Consumer<Integer> consumer) {
        for (int i = 0; i < CAPACITY; i++) {
            if (table.get(i) != null) {
                final int SIZE = table.get(i).size();
                for (int j = 0; j < SIZE; j++) {
                    consumer.accept(table.get(i).get(j));
                }
            }
        }
    }
    
    public int size() {
        return size;
    }
    
    private int hash(int value) {
        return value < 0 ? -value % CAPACITY : value % CAPACITY;
    }
    
}
