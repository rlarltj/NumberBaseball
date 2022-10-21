package engine;

import engine.model.BallCount;
import engine.model.Numbers;
import io.Input;
import io.Output;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@NoArgsConstructor
public class Baseball implements Runnable{
    private int COUNT_OF_NUMBERS;
    private int count; // 횟수 제한
    List<String> diffList = Arrays.asList("1", "2", "3");
    NumberGenerator generator;
    Input input;
    Output output;

    public Baseball(NumberGenerator generator, Input input, Output output) {
        this.generator = generator;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {

        setDifficulty();

        Numbers answer = generator.generate(COUNT_OF_NUMBERS);
        count = 10;

        while(count > 0){
            String userInput = input.input(COUNT_OF_NUMBERS+"자리 숫자를 맞춰보세요~");
            Optional<Numbers> inputNums = parse(userInput);

            if (notValidateInput(inputNums)) continue;
            count --;

            BallCount bc = ballCount(answer, inputNums.get());
            output.ballCount(bc);

            if(bc.getStrike() == COUNT_OF_NUMBERS){
                output.correct();
                return;
            }

            output.countChance(count);
        }
        output.printAnswer(answer);
    }

    private boolean notValidateInput(Optional<Numbers> inputNumbers) {
        if (inputNumbers.isEmpty()) {
            output.inputError();
            return true;
        }
        return false;
    }

    private void setDifficulty() {
        while(true){
            String difficulty = input.setDifficulty("1~3 중 원하시는 난이도를 입력해주세요");

            if(diffList.contains(difficulty)){
                int selectDifficulty = Integer.parseInt(difficulty);
                this.COUNT_OF_NUMBERS = selectDifficulty + 2;
                break;
            }
        }
    }

    private void parseDifficulty(String diff) {
        List<String> diffList = Arrays.asList("1", "2", "3");

        if(diffList.contains(diff)){
            int selectDifficulty = Integer.parseInt(diff);
            this.COUNT_OF_NUMBERS = selectDifficulty + 2;
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
