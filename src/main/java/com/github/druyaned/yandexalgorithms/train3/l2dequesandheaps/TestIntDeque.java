package com.github.druyaned.yandexalgorithms.train3.l2dequesandheaps;

public class TestIntDeque {
    
    public static void main(String[] args) {
        IntDeque deque = new IntDeque(7);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.show();
        deque.removeFirst();
        deque.show();
        for (int i = 4; i < 20; ++i) {
            if (!deque.addLast(i)) {
                break;
            }
        }
        deque.show();
        deque.removeLast();
        deque.show();
        deque.removeLast();
        deque.show();
        deque.addFirst(1);
        deque.show();
        deque.addFirst(0);
        deque.show();
        if (deque.addFirst(10)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
        for (int i = 0; i < 20; ++i) {
            if (i % 2 == 0) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
            deque.show();
            if (deque.size() == 0) {
                break;
            }
        }
    }
    
}
