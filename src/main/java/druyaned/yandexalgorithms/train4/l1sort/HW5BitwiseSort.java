package druyaned.yandexalgorithms.train4.l1sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5BitwiseSort {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
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
    
    private static void show(int n, String[] arr) {
        System.out.printf("%s", arr[0], 0);
        for (int digit = 1; digit < n; ++digit) {
            System.out.printf(" %s", arr[digit], digit);
        }
        System.out.println();
    }
    
    private static void show(int n, int[] arr) {
        System.out.printf("%d", arr[0], 0);
        for (int digit = 1; digit < n; ++digit) {
            System.out.printf(" %d", arr[digit], digit);
        }
        System.out.println();
    }
    
    private static void clear(int n, int[] arr) {
        for (int digit = 0; digit < n; ++digit) {
            arr[digit] = 0;
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final int ALPHABET_SIZE = 10;
        final String SPACER = "**********\n";
        int n = readInt(reader);
        String[] arr = new String[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = reader.readLine();
        }
        writer.write("Initial array:\n" + arr[0]);
        for (int i = 1; i < n; ++i) {
            writer.write(", " + arr[i]);
        }
        writer.write('\n');
        int length = arr[0].length();
        String[] copy = new String[n];
        int[] counts = new int[ALPHABET_SIZE];
        int[] indexes = new int[ALPHABET_SIZE];
        StringBuilder builder = new StringBuilder((2 + length) * n);
        for (int phase = 1; phase <= length; ++phase) {
            clear(ALPHABET_SIZE, counts);
            clear(ALPHABET_SIZE, indexes);
            writer.write(SPACER + "Phase " + phase + "\n");
            int pos = length - phase;
            for (int i = 0; i < n; ++i) {
                counts[arr[i].charAt(pos) - '0']++;
            }
            int total = 0;
            for (int digit = 0; digit < ALPHABET_SIZE; ++digit) {
                if (counts[digit] > 0) {
                    indexes[digit] = total;
                    total += counts[digit];
                }
            }
            for (int i = 0; i < n; ++i) {
                copy[indexes[arr[i].charAt(pos) - '0']++] = arr[i];
            }
            for (int i = 0; i < n; ++i) {
                arr[i] = copy[i];
            }
            for (int digit = 0; digit < ALPHABET_SIZE; ++digit) {
                writer.write("Bucket " + digit + ": ");
                if (counts[digit] == 0) {
                    writer.write("empty\n");
                } else {
                    int start = indexes[digit] - counts[digit];
                    builder.append(arr[start]);
                    for (int i = start + 1; i < indexes[digit]; ++i) {
                        builder.append(", ").append(arr[i]);
                    }
                    builder.append('\n');
                    writer.write(builder.toString());
                    builder.setLength(0);
                }
            }
            System.out.print("\n counts: "); // TODO: debug
            show(ALPHABET_SIZE, counts); // TODO: debug
            System.out.print("indexes: "); // TODO: debug
            show(ALPHABET_SIZE, indexes); // TODO: debug
            System.out.print("    arr: "); // TODO: debug
            show(n, arr); // TODO: debug
        }
        writer.write(SPACER + "Sorted array:\n" + arr[0]);
        for (int i = 1; i < n; ++i) {
            writer.write(", " + arr[i]);
        }
        writer.write('\n');
    }
    
}
/*
n=5
265 641 160 552 446

#3
0: 
1: 160
2: 265
3: 
4: 446
5: 552
6: 641

#2
0: 
1: 
2: 
3: 
4: 641 446
5: 552
6: 160 265

4: 2 | 0
5: 1 | 2
6: 2 | 3
ind: 0 1 2 3 4
pos: 4   5 6
ind:   0   1   2   3   4
arr: 641 446 552 160 265

#1
0: 160
1: 641
2: 552
3: 
4: 
5: 265
6: 446
*/