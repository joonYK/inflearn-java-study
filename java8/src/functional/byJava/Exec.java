package functional.byJava;

import java.util.function.*;

public class Exec {

    public static void main(String[] args) {
        //Function<T, R>
        //T 타입의 값을 받아서 R 타입의 값을 반환
        Plus10 plus10 = new Plus10();
        System.out.println(plus10.apply(1));

        Function<Integer, Integer> plus11 = i -> i + 11;
        System.out.println(plus11.apply(1));

        Function<Integer, Integer> multiply2 = i -> i * 2;

        //함수를 연결
        //입력값을 가지고 multiply2를 적용하고, 그 결과값을 plus10의 입력값으로 사용
        Function<Integer, Integer> multiply2ThenPlus10 = plus10.compose(multiply2);
        System.out.println(multiply2ThenPlus10.apply(2));

        //입력값을 plus10을 먼저 적용하고, multiply2를 적용.
        Function<Integer, Integer> plus10ThenMultiply2 = plus10.andThen(multiply2);
        System.out.println(plus10ThenMultiply2.apply(2));

        System.out.println();

        //Consumer<T>
        //T 타입을 받아서 아무것도 리턴하지 않는 인터페이스
        Consumer<Integer> printT = System.out::println;
        printT.accept(10);

        System.out.println();

        //Supplier<T>
        //T 타입의 값을 제공하는 함수 인터페이스
        Supplier<Integer> get10 = () -> 10;
        System.out.println(get10.get());

        System.out.println();

        //Predicate<T>
        //T 타입의 값을 받아서 true/false 반환
        Predicate<String> startsWithJoonyeop = s -> s.startsWith("joonyeop");
        System.out.println(startsWithJoonyeop.test("joonyeop"));
        System.out.println(startsWithJoonyeop.test("joon"));

        System.out.println();

        //UnaryOperator<T>
        //입력값의 타입과 반환값의 타입이 T
        UnaryOperator<Integer> plus12 = i -> i + 12;
        System.out.println(plus12.apply(1));

        //BiFunction<T, U, R>
        //T, R 타입의 값 2개를 받아서 U 타입의 값 반환

        //BinaryOperator<T>
        //T 타입의 값 2개를 받아서 T 타입의 값 반환
    }
}
