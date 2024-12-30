package druyaned.yandexalgorithms.train1.l2linearsearch;

import java.math.BigInteger;
import java.util.Scanner;

public class HW8MaxMultiplicationOfThreeNumbers {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] elements = line.split(" ");
        final int N = elements.length;
        int[] numbers = new int[N];
        for (int i = 0; i < N; ++i) {
            numbers[i] = Integer.parseInt(elements[i]);
        }
        System.out.println(solve(numbers));
    }
    
    public static String solve(int[] numbers) {
        //   min3 min2 min1 max1 max2 max3
        // --|----|----|----|----|----|---->
        int min3, min2, min1, max1, max2, max3;
        if (numbers[0] < numbers[1]) {
            if (numbers[0] < numbers[2]) {
                min3 = max1 = numbers[0];
                if (numbers[1] < numbers[2]) {  // 4 5 6
                    min2 = max2 = numbers[1];
                    min1 = max3 = numbers[2];
                } else {                        // 4 6 5
                    min2 = max2 = numbers[2];
                    min1 = max3 = numbers[1];
                }
            } else {                            // 5 6 4
                min3 = max1 = numbers[2];
                min2 = max2 = numbers[0];
                min1 = max3 = numbers[1];
            }
        } else {
            if (numbers[1] < numbers[2]) {
                min3 = max1 = numbers[1];
                if (numbers[0] < numbers[2]) {  // 5 4 6
                    min2 = max2 = numbers[0];
                    min1 = max3 = numbers[2];
                } else {                        // 6 4 5
                    min2 = max2 = numbers[2];
                    min1 = max3 = numbers[0];
                }
            } else {                            // 6 5 4
                min3 = max1 = numbers[2];
                min2 = max2 = numbers[1];
                min1 = max3 = numbers[0];
            }
        }
        for (int i = 3; i < numbers.length; ++i) {
            if (numbers[i] < min3) {
                min1 = min2;
                min2 = min3;
                min3 = numbers[i];
            } else if (numbers[i] < min2) {
                min1 = min2;
                min2 = numbers[i];
            } else if (numbers[i] < min1) {
                min1 = numbers[i];
            }
            if (max3 < numbers[i]) {
                max1 = max2;
                max2 = max3;
                max3 = numbers[i];
            } else if (max2 < numbers[i]) {
                max1 = max2;
                max2 = numbers[i];
            } else if (max1 < numbers[i]) {
                max1 = numbers[i];
            }
        }
        BigInteger mult1 = BigInteger.valueOf(min3)
                .multiply(BigInteger.valueOf(min2))
                .multiply(BigInteger.valueOf(max3));
        BigInteger mult2 = BigInteger.valueOf(max1)
                .multiply(BigInteger.valueOf(max2))
                .multiply(BigInteger.valueOf(max3));
        if (mult1.compareTo(mult2) < 0) {
            return max1 + " " + max2 + " " + max3;
        } else {
            return min3 + " " + min2 + " " + max3;
        }
    }

}
