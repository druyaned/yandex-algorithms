package com.github.druyaned.yandexalgorithms.train1.l2linearsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HW1AscendingList {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        for (String element : line.split(" ")) {
            list.add(Integer.valueOf(element));
        }
        for (int i = 1; i < list.size(); ++i) {
            if (list.get(i).compareTo(list.get(i - 1)) <= 0) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }

}
