package druyaned.yandexalgorithms.train1.l6binsearch;

import java.util.Scanner;

/**
 * Юра решил подготовиться к собеседованию в Яндекс. Он выбрал на сайте leetcode
 * N задач. В 1-й день Юра решил K задач, а в каждый следующий день Юра решал
 * на одну задачу больше, чем в предыдущий день.
 * <br>Определите, сколько дней уйдет у Юры на подготовку к собеседованию.
 * 
 * @author druyaned
 */
public class Task2InterviewPrepare {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        System.out.println(days(n, k));
    }
    
    public static int days(final int N, final int K) {
        int leftDays = 0, rightDays = N, midDays;
        while (leftDays < rightDays) {
            midDays = (leftDays + rightDays) / 2;
            if ((2 * K + midDays) * (midDays + 1) >= N * 2) {
                rightDays = midDays;
            } else {
                leftDays = midDays + 1;
            }
        }
        return leftDays + 1;
    }
    
}
/*
Input:
9 2
Output:
3
*/
/*
K + K + 1 + ... + K + days - 1
    days = 3
    K + K + 1 + K + 2 = 3 * K + 3
    (2 * K + days - 1) * days / 2 = 3 * K + 3
*/