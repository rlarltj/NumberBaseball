import engine.Baseball;
import engine.FakerNumberGenerator;
import engine.NumberGenerator;
import io.Console;
import io.Input;
import io.Output;

public class App {
    public static void main(String[] args) {
        NumberGenerator generator = new FakerNumberGenerator();
        Input input = new Console();
        Output output = new Console();

        new Baseball(generator, input, output).run();

    }
}
