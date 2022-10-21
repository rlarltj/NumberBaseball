package engine;

import engine.model.BallCount;
import engine.model.Numbers;
import io.Input;
import io.Output;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@AllArgsConstructor
public class Baseball implements Runnable{
    private final int COUNT_OF_NUMBERS = 3;

    NumberGenerator generator;
    Input input;
    Output output;

    @Override
    public void run() {
        Numbers answer = generator.generate(COUNT_OF_NUMBERS);

        while(true){
            String userInput = input.input("guess what");
            Optional<Numbers> inputNumbers = parse(userInput);

                if (inputNumbers.isEmpty()) {
                    output.inputError();
                    continue;
                }

                BallCount bc = ballCount(answer, inputNumbers.get());
                output.ballCount(bc);

                if(bc.getStrike() == COUNT_OF_NUMBERS){
                    output.correct();
                break;
            }

        }
    }

    private BallCount ballCount(Numbers answer, Numbers inputNumbers) {
        AtomicInteger strike = new AtomicInteger();
        AtomicInteger ball = new AtomicInteger();

        answer.indexedForEach(new BiConsumer<>() {

            @Override
            public void accept(Integer a, Integer i) {

                inputNumbers.indexedForEach((n, j) -> {
                    if(!a.equals(n)) return;
                    if(i.equals(j)) strike.addAndGet(1);
                    else ball.addAndGet(1);
                });
            }
        });
        return new BallCount(strike.get(), ball.get());
    }

    private Optional<Numbers> parse(String userInput) {
        if(userInput.length() != COUNT_OF_NUMBERS) return Optional.empty();

        long count = userInput.chars()
                .filter(i -> Character.isDigit(i))
                .map(i -> Character.getNumericValue(i))
                .filter(i -> i > 0)
                .distinct().count();

        if(count != COUNT_OF_NUMBERS) return Optional.empty();


        return Optional.of(
                new Numbers(userInput.chars()
                        .map(i -> Character.getNumericValue(i))
                        .boxed()
                        .toArray(Integer[]::new))
        );
    }
}
