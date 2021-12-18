package methodReference;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Exec {

    public static void main(String[] args) {
        //스태틱 메서드 참조
        UnaryOperator<String> hi = Greeting::hi;
        System.out.println(hi.apply("joonyeop"));

        //특정 객체의 인스턴스 메서드 참조
        Greeting greeting = new Greeting();
        UnaryOperator<String> hello = greeting::hello;
        System.out.println(hello.apply("joonyeop"));

        //생성자 참조
        Supplier<Greeting> newGreeting = Greeting::new;
        Greeting nonParamGreeting = newGreeting.get();
        System.out.println(nonParamGreeting.hello("joonyeop"));

        //파라미터 있는 생성자 참조
        Function<String, Greeting> newGreetingByParam = Greeting::new;
        Greeting paramGreeting = newGreetingByParam.apply("joonyeop");
        System.out.println(paramGreeting.hello());

        //임의 객체의 인스턴스 메소드 참조
        String[] names = {"bbb", "ccc", "aaa"};
        Arrays.sort(names, String::compareToIgnoreCase);
        System.out.println(Arrays.toString(names));
    }
}
