package com.github.druyaned.yandexalgorithms.train1.lect7;

import java.util.Arrays;

/**
 * Лекция 7 задача 1.
 * Сайт посетило N человек, для каждого известно время входа на сайт
 * In и время выхода с сайта Out. Считается, что человек был на сайте
 * с момента In по Out включительно.
 * <br>Определите, какое максимальное количество человек было на сайте одновременно.
 * 
 * @author druyaned
 */
public class Task1NumberOfVisitors {
    
    public static void main(String[] args) {
        int[] ins =  {16, 3, 6, 11, 1, 6};
        int[] outs = {22, 8, 12, 20, 4, 13};
        int n = ins.length;
        System.out.println(maxAmountAtTheSameTime(n, ins, outs));
    }
    
    private static class Event implements Comparable<Event> {

        
        private static enum Type {IN, OUT};
        
        private final int time;
        private final Type type;
        
        private Event(int time, Type type) {
            this.time = time;
            this.type = type;
        }
        
        @Override
        public int compareTo(Event o) {
            if (this.time == o.time) {
                return this.type == o.type ? 0 : this.type == Type.OUT ? 1 : -1;
            }
            return this.time - o.time;
        }
        
        @Override
        public String toString() {
            return time + "|" + (type == Type.IN ? "IN" : "OUT");
        }
        
    }
    
    public static int maxAmountAtTheSameTime(final int N, final int[] ins, final int[] outs) {
        Event[] events = new Event[N * 2];
        for (int i = 0; i < N; ++i) {
            events[i * 2] = new Event(ins[i], Event.Type.IN);
            events[i * 2 + 1] = new Event(outs[i], Event.Type.OUT);
        }
        Arrays.sort(events);
        int currentAmount = 0;
        int maxAmount = 0;
        for (int i = 0; i < events.length; ++i) {
            if (events[i].type == Event.Type.IN) {
                ++currentAmount;
                if (currentAmount > maxAmount) {
                    maxAmount = currentAmount;
                }
            } else {
                --currentAmount;
            }
        }
        return maxAmount;
    }
    
}
