package druyaned.yandexalgorithms.train4.l4bruteforce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW1AllPermutations {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader); // n in [1, 9]
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        print(writer, n, a);
        while (nextPermutation(n, a)) {
            print(writer, n, a);
        }
    }
    
    private static void print(BufferedWriter writer, int n, int[] a) throws IOException {
        for (int i = 0; i < n; i++) {
            writer.write('0' + a[i]);
        }
        writer.write('\n');
    }
    
    private static boolean nextPermutation(int n, int[] a) {
        int j = n - 2;
        while (j > -1 && a[j] > a[j + 1]) {
            j--;
        }
        if (j <= -1) {
            return false;
        }
        int k = n - 1;
        while (a[j] > a[k]) {
            k--;
        }
        swap(j, k, a);
        for (int l = j + 1, r = n - 1; l < r; l++, r--) {
            swap(l, r, a);
        }
        return true;
    }
    
    private static void swap(int i, int j, int[] a) {
        int toSwap = a[i];
        a[i] = a[j];
        a[j] = toSwap;
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
Задача: вывести все перестановки в лексикографическом порядке.
Пример:
123
132
213
231
312
321
Решение: {
    Самая первая перестановка - 1234567, а самая последняя - 7654321;
    стремлюсь от возрастающей к убывающей последовательности.
    Допустим у меня имеется какая-то перестановка: 2753641.
    Как определить следующую сразу за ней?
    Надо найти последнюю возрастающую пару: 275[36]41 - и запомнить
    левое число пары (3).
    Все элементы от правого из пары до самого последнего будут убывающими (641).
    Надо найти из оставшихся (641) ближайшего большего к левому из пары (4).
    Свапнуть левого и ближайшего большего: {...3641 -> ...4631} - и отсортировать
    по возрастанию от правого из пары до конца: {...631 -> ...136}.
    Итог: 2753641 -> 2754136.
}
Описание алгоритма:
1) ищу последние возрастающие 2 эл-та, левого из них отмечаю (j);
2) ищу ближайший больший эл-т к отмеченному (k);
3) swap(j, k), sort(j + 1, n - 1).
*/