package com.github.druyaned.yandexalgorithms.train1.l5twopointers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW9Robot {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            quiteNiceSolve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void badSolve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int k = Integer.parseInt(reader.readLine());
        String s = reader.readLine();
        int n = s.length();
        // quiteNiceSolve
        long cnt = 0;
        for (int l = 0, r = l + k; r < n; ++l, ++r) {
            for (int i = 0; r + i < n && s.charAt(l + i) == s.charAt(r + i); ++i) {
                ++cnt;
            }
        }
        writer.write(cnt + "\n");
    }
    
    public static void quiteNiceSolve(BufferedReader reader, BufferedWriter writer)
            throws IOException {
        int k = Integer.parseInt(reader.readLine());
        String s = reader.readLine();
        int n = s.length();
        // quiteNiceSolve
        long cnt = 0;
        for (int l = 0, r = l + k; r < n;) {
            int i = 0;
            while (r + i < n && s.charAt(l + i) == s.charAt(r + i)) {
                ++i;
            }
            cnt += (long)(i + 1) * i / 2L;
            l += i + 1;
            r += i + 1;
        }
        writer.write(cnt + "\n");
    }
    
}
/*
Предполагается использовать робота один раз для выполнения части
подряд идущих операций из процесса сборки.

Память робота состоит из K ячеек, каждая из которых содержит одну операцию.
Операции выполняются последовательно, начиная с первой, в том порядке,
в котором они расположены в памяти. Выполнив последнюю из них,
робот продолжает работу с первой. Робота можно остановить после любой операции.
Использование робота экономически целесообразно, если он выполнит хотя бы K + 1 операцию.

Требуется написать программу, которая по заданному процессу сборки определит
количество экономически целесообразных способов использования робота.

n=6 k=3
3
abcabc
abc.a
abc.ab
abc.abc
bca.b
bca.bc
cab.c
6 = (3 + 2 + 1) = (1 + 3)*(3 - 1 + 1)/2

2
zabacabab
 1 2 45
ab.a
ac.a
ab.ab
ba.b
5 = 1 + 1 + (2 + 1)

Плохое решение (O(n^2)):
2 указателя. Проход по l in [0, n), r = l + k.
subS = s.substring(l, l + k).
По r иду, пока s[l + i] == s[r + i] {++cnt}.

Хорошее решение (O(n)):
2 указателя. Проход по l in [0, n), r = l + k.
По r иду, пока s[l + i] == s[r + i].
После внутреннего цикла cnt += (1 + i)*i/2, l += i + 1, r += i + 1;
*/
