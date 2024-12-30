package druyaned.yandexalgorithms.train1.l2linearsearch;

import java.util.Scanner;

public class HW10TriangleOfMaxim {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // '\n' in an input queue
        double[] frequencies = new double[n];
        String[] attitudes = new String[n];
        frequencies[0] = scanner.nextDouble();
        for (int i = 1; i < n; ++i) {
            frequencies[i] = scanner.nextDouble();
            attitudes[i] = scanner.next();
        }
        System.out.println(answer(n, frequencies, attitudes));
    }
    
    public static String answer(final int N, double[] frequencies, String[] attitudes) {
        double minFrequency = 30.0d, maxFrequency = 4000.0d;
        for (int i = 1; i < N; ++i) {
            double midValue = (frequencies[i] + frequencies[i - 1]) / 2.0d;
            if (attitudes[i].equals("closer")) {
                if (frequencies[i] <= frequencies[i - 1]) {
                    if (midValue < maxFrequency) {
                        maxFrequency = midValue;
                    }
                } else {
                    if (minFrequency < midValue) {
                        minFrequency = midValue;
                    }
                }
            } else {
                if (frequencies[i] <= frequencies[i - 1]) {
                    if (minFrequency < midValue) {
                        minFrequency = midValue;
                    }
                } else {
                    if (midValue < maxFrequency) {
                        maxFrequency = midValue;
                    }
                }
            }
        }
        return minFrequency + " " + maxFrequency;
    }

}
