package druyaned.yandexalgorithms.train1.l2linearsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HW2SequenceType {

    public static void main(String[] args) {
        List<Integer> sequence = new ArrayList<>();
        int stopValue = -2000000000;
        //  INT_MAX   = +2147483647
        // 2 147 483 647
        final Scanner scanner = new Scanner(System.in);
        int inputValue = scanner.nextInt();
        while (inputValue != stopValue) {
            sequence.add(inputValue);
            inputValue = scanner.nextInt();
        }
        boolean isConstant = true;
        for (int i = 1; i < sequence.size(); ++i) {
            if (sequence.get(i - 1).compareTo(sequence.get(i)) != 0) {
                isConstant = false;
                break;
            }
        }
        if (isConstant) {
            System.out.println("CONSTANT");
            return;
        }
        boolean isAscending = true;
        for (int i = 1; i < sequence.size(); ++i) {
            if (sequence.get(i - 1).compareTo(sequence.get(i)) >= 0) {
                isAscending = false;
                break;
            }
        }
        if (isAscending) {
            System.out.println("ASCENDING");
            return;
        }
        boolean isWeaklyAscending = true;
        for (int i = 1; i < sequence.size(); ++i) {
            if (sequence.get(i - 1).compareTo(sequence.get(i)) > 0) {
                isWeaklyAscending = false;
                break;
            }
        }
        if (isWeaklyAscending) {
            System.out.println("WEAKLY ASCENDING");
            
            return;
        }
        boolean isDescending = true;
        for (int i = 1; i < sequence.size(); ++i) {
            if (sequence.get(i - 1).compareTo(sequence.get(i)) <= 0) {
                isDescending = false;
                break;
            }
        }
        if (isDescending) {
            System.out.println("DESCENDING");
            return;
        }
        boolean isWeaklyDescending = true;
        for (int i = 1; i < sequence.size(); ++i) {
            if (sequence.get(i - 1).compareTo(sequence.get(i)) < 0) {
                isWeaklyDescending = false;
                break;
            }
        }
        if (isWeaklyDescending) {
            System.out.println("WEAKLY DESCENDING");
            return;
        }
        System.out.println("RANDOM");
    }

}
