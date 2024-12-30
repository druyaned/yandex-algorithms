package druyaned.yandexalgorithms.train4.warmup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW09CorrectBracketSequence {
    
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
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line = reader.readLine();
        if (line == null) {
            writer.write("yes\n");
            return;
        }
        char[] seq = line.toCharArray();
        int n = seq.length;
        char[] stack = new char[n];
        int size = 0;
        for (int i = 0; i < n; ++i) {
            final char c = seq[i];
            if (c == '(' || c == '[' || c == '{') {
                stack[size++] = c;
            }
            if (size == 0 && (c == ')' || c == ']' || c == '}')) {
                writer.write("no\n");
                return;
            }
            if (c == ')' && stack[--size] != '(') {
                writer.write("no\n");
                return;
            }
            if (c == ']' && stack[--size] != '[') {
                writer.write("no\n");
                return;
            }
            if (c == '}' && stack[--size] != '{') {
                writer.write("no\n");
                return;
            }
        }
        if (size == 0) {
            writer.write("yes\n");
        } else {
            writer.write("no\n");
        }
    }
    
}
