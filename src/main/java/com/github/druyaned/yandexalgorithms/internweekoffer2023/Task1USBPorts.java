package com.github.druyaned.yandexalgorithms.internweekoffer2023;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <a href="https://contest.yandex.ru/contest/50834/problems/">Contest problems</a>.
 * @author ed
 */
public class Task1USBPorts {
    
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"));
                BufferedWriter writer = Files.newBufferedWriter(Paths.get("output.txt"))) {
            solve(reader, writer);
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }
    
    public static void solve(BufferedReader reader, BufferedWriter writer) throws IOException {
        long n = Long.parseLong(reader.readLine());
        long m = Long.parseLong(reader.readLine());
        long c2 = Long.parseLong(reader.readLine());
        long c5 = Long.parseLong(reader.readLine());
        long left = m - n;
        if (left <= 0) {
            writer.write("0\n");
            return;
        }
        long c2ToCompare = c2 * 4;
        long c5ToCompare = c5;
        if (c2ToCompare <= c5ToCompare) {
            writer.write((left * c2) + "\n");
        } else {
            long cost = (left / 4) * c5;
            left %= 4;
            if (left * c2 <= c5) {
                cost += left * c2;
            } else {
                cost += c5;
            }
            writer.write(cost + "\n");
        }
    }
    
}
/*
Ввод:
n - количество usb-портов
m - количество usb-гаджетов
c2 - стоимость разветвителя с 1 на 2
с5 - стоимость разветвителя с 1 на 5
Вывод:
cost - минимальная сумма, чтоб подключить все устройства

Ход решения
Сначала вычисляю сравнительную стоимости для c2 и c5: comp2 comp5.
left = m - n;
Если left > 4, то считаю по минимальному среди comp2 и comp5.
Если comp2 минимальный, то просто вывожу left * c2.
Если comp5 минимульный, то сначала считаю left / 4 * c5,
а потом смотрю по остатку, что лучше докупить.
*/
