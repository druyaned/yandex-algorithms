package com.github.druyaned.yandexalgorithms.train4.l4bruteforce;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HW5Brackets2 {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        int n = readInt(reader); // n in [0, 16]
        if (n == 0 || n % 2 != 0) {
            return;
        }
        long bitmask = minFill(n, 0, 0);
        writeBrackets(writer, n, bitmask);
        long lastMask = 0;
        for (int i = 0; i < n; i += 2) {
            lastMask = setCode(lastMask, i, 1);
            lastMask = setCode(lastMask, i + 1, 3);
        }
        while (bitmask != lastMask) {
            long closeStack = 0L;
            int size = 0;
            for (int i = n - 1; i >= 0; i--) {
                long code = getCode(bitmask, i);
                if (code > 1) {
                    closeStack = setCode(closeStack, size++, code);
                }
                if (code == 0) {
                    size--;
                    bitmask = setCode(bitmask, i, 1);
                    bitmask = minFill(n, bitmask, i + 1);
                    break;
                }
                if (code == 1) {
                    size--;
                    if (size > 0) {
                        bitmask = setCode(bitmask, i, getCode(closeStack, size - 1));
                        bitmask = minFill(n, bitmask, i + 1);
                        break;
                    }
                }
            }
            writeBrackets(writer, n, bitmask);
        }
    }
    
    private static long minFill(int n, long bitmask, int i) {
        long openStack = 0L;
        int size = 0;
        int j = 0;
        while (j < i) {
            long code = getCode(bitmask, j++);
            if (code < 2L) {
                openStack = setCode(openStack, size++, code);
            } else {
                size--;
            }
        }
        while (size < n - j) {
            bitmask = setCode(bitmask, j++, 0);
            openStack = setCode(openStack, size++, 0);
        }
        while (size > 0) {
            bitmask = setCode(bitmask, j++, getCode(openStack, --size) + 2);
        }
        return bitmask;
    }
    
    private static long getCode(long bitmask, int i) {
        return ((bitmask & 1L << i * 2) | (bitmask & 1L << i * 2 + 1)) >> i * 2;
    }
    
    private static char getBracket(long code) {
        return code == 0L ? '(' : code == 1L ? '[' : code == 2L ? ')' : code == 3L ? ']' : '#';
    }
    
    private static long setCode(long bitmask, int i, long code) {
        return bitmask & ~(3L << i * 2) | code << i * 2;
    }
    
    private static void writeBrackets(BufferedWriter writer, int n, long bitmask)
            throws IOException {
        for (int i = 0; i < n; i++) {
            writer.write(getBracket(getCode(bitmask, i)));
        }
        writer.write('\n');
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
Генерация правильных скобочных последовательностей
в лексикографическом порядке.

Замены:
( -> 0 -> 00
[ -> 1 -> 01
) -> 2 -> 10
] -> 3 -> 11

Пример для n=6:
((())) 000222
(([])) 001322
(()()) 002022
(()[]) 002132
(())() 002202
(())[] 002213
([()]) 010232
([[]]) 011332
([]()) 013022
([][]) 013132
([])() 013202
([])[] 013213
()(()) 020022
()([]) 020132
()()() 020202
()()[] 020213
()[()] 021023
()[[]] 021133
()[]() 021302
()[][] 021313
[(())] 100223
[([])] 101323
[()()] 102023
[()[]] 102133
[()]() 102302
[()][] 102313
[[()]] 110233
[[[]]] 111333
[[]()] 113023
[[][]] 113133
[[]]() 113302
[[]][] 113313
[](()) 130022
[]([]) 130132
[]()() 130202
[]()[] 130213
[][()] 131023
[][[]] 131133
[][]() 131302
[][][] 131313

Описание алгоритма:
0)  буду проходиться справа налево (i = n-1);
    заведу стек закрывающихся скобок (closeStack),
        который буду обновлять на каждом шаге;
1)  если b[i] == 2 или 3 (закрывающие скобки), то иду дальше;
    если b[i] == 0 (1-я открывающая скобка), то b[i] = 1 и b[i+1] = 3,
        оставшуюся часть справа заполняю минимальным образом и вывожу скобочки;
    если b[i] == 1 и closeStack не пуст, то b[i] = closeStack[last],
        оставшуюся часть справа заполняю минимальным образом и вывожу скобочки,
    а иначе просто иду дальше;

Заполнение минимальным образом:
0)  буду проходиться слева направо (j = 0);
    завожу стек открытых скобок (openStack),
        который буду обновлять на каждом шаге;
1)  прохожусь до заданной позиции i;
2)  заполняю маску 0-ми, пока не дойду до момента,
        когда в стеке будет столько открывающих скобок,
        сколько осталось места справа;
3)  оставшееся место заполняю закрывающими скобками по стеку.

Пример перевода скобок в битовую маску:
([]) -> 0132 -> 00 01 11 10 -> 10 11 01 00 -> 0xb4
*/
