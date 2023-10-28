package com.github.druyaned.yandexalgorithms.train1.l1complexity;

import java.util.Scanner;

public class HW1Conditioner {
    
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int tRoom = scanner.nextInt(); // [-50, 50]
        int tCond = scanner.nextInt(); // [-50, 50]
        scanner.nextLine();
        String mode = scanner.nextLine(); // {freeze, heat, auto, fan}
        HW1Conditioner hw1 = new HW1Conditioner();
        System.out.println(hw1.tResult(tRoom, tCond, mode));
    }
    
    public int tResult(int tRoom, int tCond, String mode) {
        switch (mode) {
            case "freeze" -> {
                return tRoom < tCond ? tRoom : tCond;
            }
            case "heat" -> {
                return tRoom < tCond ? tCond : tRoom;
            }
            case "auto" -> {
                return tCond;
            }
            case "fan" -> {
                return tRoom;
            }
            default -> {
                throw new IllegalArgumentException("wrong mode");
            }
        }
    }

}
