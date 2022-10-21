package io;

import engine.model.BallCount;
import engine.model.Numbers;

public interface Output {
    void ballCount(BallCount bc);

    void inputError();

    void correct();

    void countChance(int count);

    void printAnswer(Numbers answer);
}
