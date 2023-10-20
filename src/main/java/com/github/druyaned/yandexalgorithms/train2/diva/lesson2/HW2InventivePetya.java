package com.github.druyaned.yandexalgorithms.train2.diva.lesson2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW2InventivePetya {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    private static boolean isEqual(char[] s1, char[] s2, int end2) {
        for (int i1 = s1.length - 1, i2 = end2 - 1; i2 >= 0; --i1, --i2) {
            if (s1[i1] != s2[i2]) {
                return false;
            }
            if (i1 == 0) {
                i1 = s1.length;
            }
        }
        return true;
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        char[] x = reader.readLine().toCharArray();
        char[] z = reader.readLine().toCharArray();
        int endZ = z.length;
        while (endZ > 0 && !isEqual(x, z, endZ)) {
            --endZ;
        }
        for (int i = endZ; i < z.length; ++i) {
            writer.write(z[i]);
        }
        writer.write('\n');
    }
    
}
/*
input:
yeyayiyu
yiyuyeyayiyuulula
output:
ulala

yeyayiyu
yiyu|yeyayiyu|ulula

Надо идти с конца z.
И в чем суть прохода? Когда начинается конец x,
то фиксирую текущую позицию z и проверяю, все ли чики-дрики.
Если да, то можно и поесть, а если нет, то, Миша, давай по-новой.
*/
