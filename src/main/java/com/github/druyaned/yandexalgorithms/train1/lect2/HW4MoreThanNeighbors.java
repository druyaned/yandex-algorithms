package com.github.druyaned.yandexalgorithms.train1.lect2;

import java.util.Scanner;

public class HW4MoreThanNeighbors {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] lineElements = line.split(" ");
        final int N = lineElements.length;
        int[] list = new int[N];
        for (int i = 0; i < N; ++i) {
            list[i] = Integer.parseInt(lineElements[i]);
        }
        int count = 0;
        for (int i = 1; i < N - 1; ++i) {
            if (list[i - 1] < list[i] && list[i + 1] < list[i]) {
                ++count;
            }
        }
        System.out.println(count);
    }

}
