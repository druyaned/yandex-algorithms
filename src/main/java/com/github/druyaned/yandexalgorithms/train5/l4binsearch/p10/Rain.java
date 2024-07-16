package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Rain {
    
    private static final double P = 1e-6;
    private static final Buffer buf = new Buffer();
    private static final Array indexes = new Array();
    private static int n;
    private static double h;
    private static int x[], y[];
    private static double pourLeft[], pourRight[];
    
    public static void main(String[] args) {
        Path fin = Paths.get("input.txt");
        Path fout = Paths.get("output.txt");
        try (
                BufferedReader reader = Files.newBufferedReader(fin);
                BufferedWriter writer = Files.newBufferedWriter(fout);
        ) {
            solve(new SimpleScanner(reader, writer));
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(SimpleScanner scanner) throws IOException {
        // init
        n = scanner.readInt() + 1;
        h = scanner.readDouble();
        x = new int[n + 2];
        y = new int[n + 2];
        y[0] = (int)1e8;
        y[n + 1] = (int)1e8 + 1;
        for (int i = 1; i <= n; i++) {
            x[i] = scanner.readInt();
            y[i] = scanner.readInt();
        }
        x[0] = x[1];
        x[n + 1] = x[n];
        // pourLeft
        pourLeft = new double[n + 2];
        for (int i = 2; i <= n; i++) {
            if (y[i - 1] > y[i]) {
                pourLeft[i] = (x[i] - x[i - 1]) * h + pourLeft[i - 1];
            } else {
                int il = leftWalk(i, y[i]);
                double water = (x[i] - x[il]) * h + pourLeft[il];
                double area = buf.gaussArea();
                pourLeft[i] = water > area ? water - area : 0d;
            }
        }
        // pourRight
        pourRight = new double[n + 2];
        for (int i = n - 1; i >= 1; i--) {
            if (y[i] < y[i + 1]) {
                pourRight[i] = (x[i + 1] - x[i]) * h + pourRight[i + 1];
            } else {
                int ir = rightWalk(i, y[i]);
                double water = (x[ir] - x[i]) * h + pourRight[ir];
                double area = buf.gaussArea();
                pourRight[i] = water > area ? water - area : 0d;
            }
        }
        // prepare for binSearch
        for (int i = 1; i <= n; i++) {
            if (y[i - 1] > y[i] && y[i] < y[i + 1]) {
                indexes.add(i);
            }
        }
        // binary search
        double leftH = 0d, rightH = 1.1e9;
        while (rightH - leftH > P) {
            double midH = (rightH + leftH) / 2d;
            if (check(midH)) {
                leftH = midH;
            } else {
                rightH = midH;
            }
        }
        scanner.write(String.format("%.4f\n", leftH));
    }
    
    private static int leftWalk(int i, double yh) {
        buf.clear();
        buf.add(x[i], yh);
        if (y[i] < yh) {
            buf.add(x[i], y[i]);
        }
        int il = i - 1;
        while (il >= 1 && y[il] < yh) {
            buf.add(x[il], y[il]);
            il--;
        }
        buf.add(intersectionX(x[il], y[il], x[il + 1], y[il + 1], yh), yh);
        return il;
    }
    
    private static int rightWalk(int i, double yh) {
        buf.clear();
        buf.add(x[i], yh);
        if (yh > y[i]) {
            buf.add(x[i], y[i]);
        }
        int ir = i + 1;
        while (ir <= n && yh > y[ir]) {
            buf.add(x[ir], y[ir]);
            ir++;
        }
        buf.add(intersectionX(x[ir], y[ir], x[ir - 1], y[ir - 1], yh), yh);
        return ir;
    }
    
    private static boolean check(double height) {
        for (int j = 0; j < indexes.size(); j++) {
            int i = indexes.get(j);
            int il = leftWalk(i, height + y[i]);
            double al = buf.gaussArea();
            int ir = rightWalk(i, height + y[i]);
            double ar = buf.gaussArea();
            double water = (x[ir] - x[il]) * h + pourLeft[il] + pourRight[ir];
            double area = al + ar;
            if (water >= area) {
                return true;
            }
        }
        return false;
    }
    
    private static double intersectionX(
            double xa, double ya,
            double xb, double yb,
            double yc
    ) {
        return (yc - ya) / (yb - ya) * (xb - xa) + xa;
    }
    
}

class Buffer {
    
    private final double[] x = new double[(int)2e4];
    private final double[] y = new double[(int)2e4];
    private int size = 0;
    
    public void add(double x, double y) {
        this.x[size] = x;
        this.y[size++] = y;
    }
    
    public double getX(int i) {
        return x[i];
    }
    
    public double getY(int i) {
        return y[i];
    }
    
    public double getLastX() {
        return x[size - 1];
    }
    
    public double getLastY() {
        return y[size - 1];
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        size = 0;
    }
    
    public double gaussArea() {
        double stock = 0d;
        final int last = size - 1;
        for (int i = 0; i < last; i++) {
            stock += x[i] * y[i + 1] - x[i + 1] * y[i];
        }
        stock += x[last] * y[0] - x[0] * y[last];
        return Math.abs(stock) / 2d;
    }
    
}

class Array {
    
    private final int[] arr = new int[(int)2e4];
    private int size = 0;
    
    public void add(int val) {
        arr[size++] = val;
    }
    
    public int get(int i) {
        return arr[i];
    }
    
    public int size() {
        return size;
    }
    
}

class SimpleScanner {
    
    private final char[] buffer = new char[(int)2e6];
    private final BufferedReader reader;
    private final BufferedWriter writer;
    
    public SimpleScanner(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }
    
    public int readInt() throws IOException {
        int bufferLength = putNextTokenToBuffer();
        return Integer.parseInt(new String(buffer, 0, bufferLength));
    }
    
    public double readDouble() throws IOException {
        int bufferLength = putNextTokenToBuffer();
        return Double.parseDouble(new String(buffer, 0, bufferLength));
    }
    
    public String readToken() throws IOException {
        int bufferLength = putNextTokenToBuffer();
        return bufferLength != 0 ? new String(buffer, 0, bufferLength) : null;
    }
    
    public void write(String s) throws IOException {
        writer.write(s);
    }
    
    public void write(char ch) throws IOException {
        writer.write(ch);
    }
    
    // following after the token whitespace is also read
    private int putNextTokenToBuffer() throws IOException {
        int ch;
        // skip whitespaces
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        // putting the token
        buffer[0] = ch == '\u2013' ? '-' : (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            buffer[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}
