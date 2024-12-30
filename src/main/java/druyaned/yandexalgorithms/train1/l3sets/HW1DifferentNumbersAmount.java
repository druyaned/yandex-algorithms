package druyaned.yandexalgorithms.train1.l3sets;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HW1DifferentNumbersAmount {
    
    public static void main(String[] args) {
        final int N = 100000;
        Scanner sin = new Scanner(System.in);
        Set<Integer> values = new HashSet<>(N);
        int count = 0;
        while (sin.hasNextInt()) {
            Integer value = sin.nextInt();
            if (!values.contains(value)) {
                values.add(value);
                ++count;
            }
        }
        System.out.println(count);
    }
    
}
