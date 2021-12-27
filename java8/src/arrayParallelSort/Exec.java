package arrayParallelSort;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Arrays.parallelSort() 를 사용해서 Fork/Join 프레임워크로 배열을 정렬. (여러 쓰레드로 분산해서 처리)
 */
public class Exec {
    public static void main(String[] args) {
        int size = 1500;
        int[] numbers = new int[size];
        Random random = new Random();

        //단일 쓰레드로 정렬
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        long start = System.nanoTime();
        Arrays.sort(numbers);
        System.out.println("serial sorting took " + (System.nanoTime() - start));

        //다중 쓰레드로 정렬
        IntStream.range(0, size).forEach(i -> numbers[i] = random.nextInt());
        start = System.nanoTime();
        Arrays.parallelSort(numbers);
        System.out.println("parallel sorting took " + (System.nanoTime() - start));
    }
}
