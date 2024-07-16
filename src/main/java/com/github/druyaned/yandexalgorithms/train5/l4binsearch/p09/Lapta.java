package com.github.druyaned.yandexalgorithms.train5.l4binsearch.p09;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lapta {
    
    private static final char[] buffer = new char[(int)2e6];
    private static final double P = 1d / (1 << 20);
    private static int d, n, x[], y[], v[];
    private static double targetX, targetY;
    
    public static void main(String[] args) {
        Path fin = Paths.get("input.txt");
        Path fout = Paths.get("output.txt");
        try (
                BufferedReader reader = Files.newBufferedReader(fin);
                BufferedWriter writer = Files.newBufferedWriter(fout);
        ) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static void solve(BufferedReader reader, BufferedWriter writer)
    throws IOException {
        // input
        d = readInt(reader);
        n = readInt(reader);
        x = new int[n];
        y = new int[n];
        v = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = readInt(reader);
            y[i] = readInt(reader);
            v[i] = readInt(reader);
        }
        double leftTime = 0d, rightTime = 4 * d;    
        while (rightTime - leftTime > P) {
            double midTime = (leftTime + rightTime) / 2d;
            if (check(midTime)) {
                leftTime = midTime;
            } else {
                rightTime = midTime;
            }
        }
        writer.write(String.format("%.5f\n", leftTime));
        writer.write(String.format("%.5f %.5f\n", targetX, targetY));
    }
    
    private static boolean check(double time) {
        Stack stack = new Stack();
        stack.push(new Rectangle(-d, 0d, d, d));
        outer: while (stack.size() > 0) {
            Rectangle rect = stack.pop();
            if (!rect.anyCovered(0d, 0d, d)) {
                continue;
            }
            if (rect.xRightUp - rect.xLeftDown < P) {
                targetX = (rect.xLeftDown + rect.xRightUp) / 2d;
                targetY = (rect.yLeftDown + rect.yRightUp) / 2d;
                return true;
            }
            for (int i = 0; i < n; i++) {
                if (rect.allCovered(x[i], y[i], v[i] * time)) {
                    continue outer;
                }
            }
            rect.divide(stack);
        }
        return false;
    }
    
    private static int readInt(Reader reader) throws IOException {
        int bufferLength = nextTokenInBuffer(reader);
        return Integer.parseInt(new String(buffer, 0, bufferLength));
    }
    
    private static int nextTokenInBuffer(Reader reader) throws IOException {
        int ch;
        while ((ch = reader.read()) != -1 && Character.isWhitespace(ch)) {}
        if (ch == -1) {
            return 0;
        }
        buffer[0] = (char)ch;
        int bufferLength = 1;
        while ((ch = reader.read()) != -1 && !Character.isWhitespace(ch)) {
            buffer[bufferLength++] = (char)ch;
        }
        return bufferLength;
    }
    
}

class Rectangle {
    
    public final double xLeftDown, yLeftDown;
    public final double xRightUp, yRightUp;
    
    public Rectangle(
            double xLeftDown, double yLeftDown,
            double xRightUp, double yRightUp
    ) {
        this.xLeftDown = xLeftDown;
        this.yLeftDown = yLeftDown;
        this.xRightUp = xRightUp;
        this.yRightUp = yRightUp;
    }
    
    public boolean allCovered(double x, double y, double radius) {
        return isCovered(xLeftDown, yLeftDown, x, y, radius) &&
                isCovered(xLeftDown, yRightUp, x, y, radius) &&
                isCovered(xRightUp, yRightUp, x, y, radius) &&
                isCovered(xRightUp, yLeftDown, x, y, radius);
    }
    
    public boolean anyCovered(double x, double y, double radius) {
        return isCovered(xLeftDown, yLeftDown, x, y, radius) ||
                isCovered(xLeftDown, yRightUp, x, y, radius) ||
                isCovered(xRightUp, yRightUp, x, y, radius) ||
                isCovered(xRightUp, yLeftDown, x, y, radius);
    }
    
    public void divide(Stack stack) {
        double xMid = (xRightUp + xLeftDown) / 2d;
        double yMid = (yRightUp + yLeftDown) / 2d;
        stack.push(new Rectangle(xLeftDown, yLeftDown, xMid, yMid));
        stack.push(new Rectangle(xLeftDown, yMid, xMid, yRightUp));
        stack.push(new Rectangle(xMid, yMid, xRightUp, yRightUp));
        stack.push(new Rectangle(xMid, yLeftDown, xRightUp, yMid));
    }
    
    private static boolean isCovered(
            double x, double y,
            double xc, double yc,
            double radius
    ) {
        double a = x - xc;
        double b = y - yc;
        double dist = Math.sqrt(a * a + b * b);
        return radius >= dist;
    }
    
}

class Stack {
    
    public static final int CAPACITY = (int)2e5;
    
    private final Rectangle[] rects = new Rectangle[CAPACITY];
    private int size = 0;
    
    public int size() {
        return size;
    }
    
    public void push(Rectangle rect) {
        rects[size++] = rect;
    }
    
    public Rectangle pop() {
        return rects[--size];
    }
    
}
