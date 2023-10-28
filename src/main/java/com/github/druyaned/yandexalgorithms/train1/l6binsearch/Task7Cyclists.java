package com.github.druyaned.yandexalgorithms.train1.l6binsearch;

/**
 * Лекция 6 задача 7.
 * Велосипедисты, участвующие в шоссейной гонке, в некоторый момент времени,
 * который называется начальным, оказались в точках, удаленных от места старта
 * на x1, x2, ..., xN метров (N - общее количество велосипедистов,
 * не первосходит 100 000). Каждый велосипедист двигается со своей постоянной скоростью
 * v1, v2, ..., vN метров в секунду. Все велосипедисты двигаются в одну сторону.
 * Репортер, освещающий ход соревнований, хочет определить момент времени,
 * в который расстояние между лидирующим в гонке велосипедистом и
 * замыкающим гонку велосипедистом станет минимальным, чтобы с вертолета
 * сфотографировать сразу всех участников велогонки.
 * <br>Необходимо найти момент времени, когда расстояние станет минимальным.
 * 
 * @author druyaned
 */
public class Task7Cyclists {
    
    public static void main(String[] args) {
        int[] xInit = {72, 60, 48, 36, 24};
        int[] v = {12, 11, 18, 15, 19};
        final int N = xInit.length;
        final int T = 7200; // race time
        int t = timeOfMinDist(N, T, xInit, v);
        System.out.println("t: " + t);
        System.out.println("dist(t - 1): " + distance(N, xInit, v, t - 1));
        System.out.println("dist(t):     " + distance(N, xInit, v, t));
        System.out.println("dist(t + 1): " + distance(N, xInit, v, t + 1));
    }
    
    public static int timeOfMinDist(final int N, final int T, int[] xInit, int[] v) {
        int leftTime = 0, rightTime = T, midTime;
        while (leftTime < rightTime) {
            midTime = (leftTime + rightTime) / 2; // < T
            int distance = distance(N, xInit, v, midTime);
            int nextDistance = distance(N, xInit, v, midTime + 1);
            if (distance <= nextDistance) {
                rightTime = midTime;
            } else {
                leftTime = midTime + 1;
            }
        }
        return leftTime;
    }
    
    private static int distance(final int N, int[] xInit, int[] v, int t) {
        int lastX = getX(v[0], t, xInit[0]);
        int leaderX = lastX;
        for (int i = 1; i < N; ++i) {
            int x = getX(v[i], t, xInit[i]);
            if (x < lastX) {
                lastX = x;
            }
            if (leaderX < x) {
                leaderX = x;
            }
        }
        return leaderX - lastX;
    }
    
    private static int getX(int v, int t, int xInit) {
        return v * t + xInit;
    }
    
}
