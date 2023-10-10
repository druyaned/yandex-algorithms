package com.github.druyaned.yandexalgorithms.train1.lect6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW4SpaceSettlement {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static long maxDepth(long n, long a, long b, long w, long h) {
        long leftD = 0L, rightD = Long.max(w - a, h - b) / 2L, midD;
        while (leftD < rightD) {
            midD = (leftD + rightD + 1) / 2;
            long sideWidth = a + 2L * midD;
            long widthN = w / sideWidth;
            long width = widthN * sideWidth;
            long sideHeight = b + 2L * midD;
            long heightN = h / sideHeight;
            long height = heightN * sideHeight;
            if (width <= w && height <= h && n <= widthN * heightN) {
                leftD = midD;
            } else {
                rightD = midD - 1;
            }
        }
        return rightD;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        long n = Long.parseLong(elems[0]);
        long a = Long.parseLong(elems[1]);
        long b = Long.parseLong(elems[2]);
        long w = Long.parseLong(elems[3]);
        long h = Long.parseLong(elems[4]);
        // solve
        long maxDepthAB = maxDepth(n, a, b, w, h);
        long maxDepthBA = maxDepth(n, b, a, w, h);
        writer.write(Long.max(maxDepthAB, maxDepthBA) + "\n");
    }
    
}
/*
sideW = a + 2*d
sideH = b + 2*d
widthN * sideW <= 3e18
widthN = totLen / sideW

height = heightN*(b + 2*d)
Правый бин-поиск, сначала все хорошо, а потом плохо,
и надо найти самый правый элемент, когда все хорошо.
Хорошо - это height <= h && width <= w.
Начальный rightD = max(w - a, h - b) / 2,
т.е. для одной строки или одного ряда.

Есть особенность.
Жилые модули модули быть расположены как a*b и как b*a.
*/
