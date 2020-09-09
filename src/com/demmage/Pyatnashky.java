package com.demmage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Pyatnashky {

    private static Integer[][] field = new Integer[4][4];
    private static final String fieldTemplate = "   /  X1 | X2 | X3 | X4\nY1 |  %d  |\t%d\t|\t%d\t|  %d   |\n" +
            "Y2 |  %d  | %d  | %d  |  %d  |\nY3 |  %d | %d  |  %d |  %d |\nY4 |  %d |  %d |  %d |  %d  |\n";

    private static boolean win = false;
    private static final String instruction = "Choose coordinates between 0 to 3";

    private static void fillField() {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        IntStream.range(1, 16).forEach(numbers::add);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (numbers.size() - 1 == 0) {
                    field[i][j] = numbers.get(0);
                    break;
                }
                int index = random.nextInt(numbers.size() - 1);
                int number = numbers.get(index);
                numbers.remove((Integer) number);
                field[i][j] = number;
            }
        }

        int randomI = random.nextInt(3);
        int randomJ = random.nextInt(3);
        int temp = field[randomI][randomJ];
        field[randomI][randomJ] = 0;
        field[3][3] = temp;
    }

    private static void printField() {
//        ArrayList<Integer> list = new ArrayList<>();
//        for (Integer[] arr : field) {
//            list.addAll(Arrays.asList(arr));
//        }
//        System.out.println(String.format(fieldTemplate, list.toArray()));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean checkWinning() {
        int previous = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int current = field[i][j];
                if (current == previous + 1) {
                    previous = current;
                } else {
                    return false;
                }
                if (i == 3 && j == 2) {
                    return true;
                }
            }
        }
        return true;
    }

    private static void init() {
        fillField();
        printField();
    }

    private static boolean validateCoordinates(int x, int y) {
        return (x <= 3 && x >= 0) && (y <= 3 && x >= 0);
    }

    public static void main(String[] args) {
        init();
        Scanner input = new Scanner(System.in);
        while (!win) {
            int getX, setX, setY, getY;
            try {
                System.out.print("Select from row\nX: ");
                getX = Integer.parseInt(input.nextLine());
                System.out.print("Y: ");
                getY = Integer.parseInt(input.nextLine());
                if (!validateCoordinates(getX, getY)) {
                    System.out.println(instruction);
                    continue;
                }
                System.out.print("Selected " + field[getX][getY] + ", move to\nX: ");
                setX = Integer.parseInt(input.nextLine());
                System.out.print("Y: ");
                setY = Integer.parseInt(input.nextLine());
                if (!validateCoordinates(setX, setY)) {
                    System.out.println(instruction);
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(instruction);
                continue;
            }
            System.out.println("Selected " + field[setX][setY]);
            if (field[setX][setY] == 0) {
                field[setX][setY] = field[getX][getY];
                field[getX][getY] = 0;
            } else {
                System.out.println("Busy cell");
            }
            win = checkWinning();
            if (win) {
                System.out.println("You win!!!");
                System.exit(0);
            }
            printField();
        }
    }
}
