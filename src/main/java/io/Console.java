package io;

import engine.model.BallCount;
import engine.model.Numbers;

import java.util.Scanner;

public class Console implements Input, Output {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String input(String s) {
        System.out.print(s);
        return scanner.nextLine();
    }

    @Override
    public String setDifficulty(String s) {
        System.out.print(s);
        return scanner.nextLine();
    }

    @Override
    public void ballCount(BallCount bc) {
        System.out.println(bc.getStrike() + " Strikes, " + bc.getBall() + " Balls");
    }

    @Override
    public void inputError() {
        System.out.println("입력이 잘못되었습니다.");
    }

    @Override
    public void correct() {
        System.out.println("정답입니다.");
    }

    @Override
    public void countChance(int count) {
        System.out.println("기회가 " + count + "번 남았습니다");
    }

    @Override
    public void printAnswer(Numbers answer) {
        StringBuilder sb = answer.printAnswer();
        System.out.println(sb);
    }
}
