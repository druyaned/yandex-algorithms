package druyaned.yandexalgorithms.internsday2023.contest;

import java.util.Arrays;
import java.util.Scanner;

public class Task2GameWithSwaps {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // '\n' in an input queue
        String[] s1 = new String[n];
        String[] s2 = new String[n];
        for (int i = 0; i < n; ++i) {
            s1[i] = scanner.nextLine();
            s2[i] = scanner.nextLine();
        }
        System.out.print(answer(n, s1, s2));
    }
    
    public static String answer(final int N, String[] s1, String[] s2) {
        final int LETTERS_COUNT = 26;
        char[] letters1 = new char[LETTERS_COUNT]; // Pair: 'a' <-> 'b'; corresponds to 'a'
        char[] letters2 = new char[LETTERS_COUNT]; // Pair: 'a' <-> 'b'; corresponds to 'b'
        StringBuilder answerBuilder = new StringBuilder();
        for (int i = 0; i < N; ++i) {
            boolean isGood = true;
            for (int j = 0; j < s1[i].length(); ++j) {
                char ch1 = s1[i].charAt(j);
                char ch2 = s2[i].charAt(j);
                int num1 = ch1 - 'a';
                int num2 = ch2 - 'a';
                if (letters1[num1] == '\0' && letters2[num2] == '\0') {
                    letters1[num1] = ch2;
                    letters2[num2] = ch1;
                } else if (letters1[num1] != '\0' && letters2[num2] != '\0') {
                    if (letters1[num1] != ch2 || letters2[num2] != ch1) {
                        isGood = false;
                        break;
                    }
                } else {
                    isGood = false;
                    break;
                }
            }
            if (isGood) {
                answerBuilder.append("YES").append('\n');
            } else {
                answerBuilder.append("NO").append('\n');
            }
            Arrays.fill(letters1, '\0');
            Arrays.fill(letters2, '\0');
        }
        return answerBuilder.toString();
    }

}
/*
 Input:
4
abcd
pqrs
abc
zyc
sssss
ppppp
xx
ab
 Output:
YES
YES
YES
NO
*/