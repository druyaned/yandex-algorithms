package druyaned.yandexalgorithms.train5.l1complexity.p06;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MishaAndMath {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        final char PLUS = '\u002b';
        final char MULTIPLY = '\u0078';
        int n = readInt(reader);
        int[] types = new int[n]; // 0 - zero, 1 - odd, 2 - even
        for (int i = 0; i < n; i++) {
            int number = readInt(reader);
            if (number == 0) {
                types[i] = 0;
            } else if (isOdd(number)) {
                types[i] = 1;
            } else {
                types[i] = 2;
            }
        }
        char[] operations = new char[n - 1];
        int operand1 = types[0];
        for (int i = 0; i < n - 1; i++) {
            int operand2 = types[i + 1];
            int sum = operand1 + operand2;
            int multiplication = operand1 * operand2;
            if (isOdd(sum)) {
                operations[i] = PLUS;
                operand1 = 1;
            } else if (isOdd(multiplication)) {
                operations[i] = MULTIPLY;
                operand1 = 1;
            } else if (sum != 0) {
                operations[i] = PLUS;
                operand1 = 2;
            } else if (multiplication != 0) {
                operations[i] = MULTIPLY;
                operand1 = 2;
            } else {
                operations[i] = PLUS;
                operand1 = 0;
            }
        }
        if (!isOdd(operand1)) {
            writer.write("-1\n");
        } else {
            for (int i = 0; i < n - 1; i++) {
                writer.write(operations[i]);
            }
            writer.write('\n');
        }
    }
    
    private static boolean isOdd(int n) {
        return n % 2 != 0;
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
    
}
/*
input:
7
5 7 2 0 4 -1 -6
output:
x+++x+

input:
4
0 0 6 6
output:
-1
*/
