package com.github.druyaned.yandexalgorithms.train1.lect2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.IntPredicate;

public class HW5Championship {
    
    private static class Participant implements Comparable<Participant> {
        
        final int range, index;
        
        Participant(int range, int index) {
            this.range = range;
            this.index = index;
        }

        @Override
        public int compareTo(Participant participant) {
            return participant.range == range ?
                    index - participant.index :
                    participant.range - range;
        }
        
    }
    
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int N = scanner.nextInt(); // [3, 10^5]
        final int[] ranges = new int[N]; // list[i] in [1, 1000]
        Participant[] participants = new Participant[N];
        for (int i = 0; i < N; ++i) {
            ranges[i] = scanner.nextInt();
            participants[i] = new Participant(ranges[i], i);
        }
        System.out.println(highestPlace(participants, ranges, N));
    }
    
    private static int highestPlace(Participant[] participants, final int[] ranges, final int N) {
        Arrays.sort(participants);
        IntPredicate meetConditions = i -> participants[i].range % 5 == 0 &&
                participants[i].range % 10 != 0 &&
                participants[i].index < N - 1 &&
                participants[0].index < participants[i].index &&
                ranges[participants[i].index + 1] < participants[i].range;
        int currentPlace = 1;
        for (int i = 1; i < N; ++i) {
            if (participants[i - 1].range != participants[i].range) {
                currentPlace = i + 1;
            }
            if (meetConditions.test(i)) {
                return currentPlace;
            }
        }
        return 0;
    }

}
