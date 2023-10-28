package com.github.druyaned.yandexalgorithms.train1.l6binsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW3Diplomas {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        String[] elems = reader.readLine().split(" ");
        int w = Integer.parseInt(elems[0]);
        int h = Integer.parseInt(elems[1]);
        int n = Integer.parseInt(elems[2]);
        // solve
        int leftWidthN = 1, rightWidthW = n, midWidthN;
        while (leftWidthN < rightWidthW) {
            midWidthN = (rightWidthW + leftWidthN) / 2;
            int heightN = (n - 1) / midWidthN + 1;
            long widthSide = (long)w * midWidthN;
            long heightSide = (long)h * heightN;
            if (heightSide <= widthSide) {
                rightWidthW = midWidthN;
            } else {
                leftWidthN = midWidthN + 1;
            }
        }
        int heightN = (n - 1) / leftWidthN + 1;
        long widthSide = (long)w * leftWidthN;
        long heightSide = (long)h * heightN;
        if (leftWidthN == 1) {
            writer.write(Long.max(widthSide, heightSide) + "\n");
            return;
        }
        int prevWidthN = leftWidthN - 1;
        int prevHeightN = (n - 1) / prevWidthN + 1;
        long prevSide = Long.max((long)w * prevWidthN, (long)h * prevHeightN);
        writer.write(Long.min(widthSide, prevSide) + "\n");
    }
    
}
/*
Стороной будет max(widthSide, heightSide).
Пусть widthN - это количество столбцов на доске, а
heightN - это количество строк на доске.
heghtN = (n - 1) / widthN + 1.
widthSide = w * widthN - общая ширина дипломов,
heightSide = h * heightN - общая высота дипломов.
Бинпоиск возвращает первый элемент, который удовлетворяет условию:
heightSide <= widthSide.
И в конце надо будет проверить предыдущую widhSide.
*/