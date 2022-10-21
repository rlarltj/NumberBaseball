package engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.function.BiConsumer;

@Getter
public class Numbers {
    private Integer[] nums;
    private StringBuilder sb;

    public Numbers(Integer[] nums) {
        this.nums = nums;
    }

    public void indexedForEach(BiConsumer<Integer, Integer> consumer) {
        for (int i = 0; i < nums.length; i++) {
            consumer.accept(nums[i], i);
        }
    }

    public StringBuilder printAnswer() {
        sb = new StringBuilder();
        sb.append("정답은 [ ");
        for (int i = 0; i < nums.length; i++) {
            sb.append(nums[i]+" ");
        }
        sb.append("]입니다.");

        return sb;
    }
}
