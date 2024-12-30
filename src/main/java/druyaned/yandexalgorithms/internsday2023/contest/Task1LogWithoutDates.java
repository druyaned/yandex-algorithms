package druyaned.yandexalgorithms.internsday2023.contest;

import java.util.Scanner;

public class Task1LogWithoutDates {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // [1, 100 000]
        scanner.nextLine(); // '\n' in an input queue
        String[] logs = new String[n]; // HH:MM:SS, H in [0, 23], M and S are in [0, 59]
        for (int i = 0; i < n; ++i) {
            logs[i] = scanner.nextLine();
        }
        System.out.println(minDays(logs, n));
    }
    
    public static int minDays(String[] logs, final int N) {
        int minDays = 1;
        String[] timeParts = logs[0].split(":");
        int h = Integer.parseInt(timeParts[0]);
        int m = Integer.parseInt(timeParts[1]);
        int s = Integer.parseInt(timeParts[2]);
        int prevDaySeconds = h * 60 * 60 + m * 60 + s;
        for (int i = 1; i < N; ++i) {
            timeParts = logs[i].split(":");
            h = Integer.parseInt(timeParts[0]);
            m = Integer.parseInt(timeParts[1]);
            s = Integer.parseInt(timeParts[2]);
            int daySeconds = h * 60 * 60 + m * 60 + s;
            if (daySeconds <= prevDaySeconds) {
                ++minDays;
            }
            prevDaySeconds = daySeconds;
        }
        return minDays;
    }

}
