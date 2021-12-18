package defaultStaticMethod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

/*
 * 인터페이스 기본 메서드를 사용할 수 있게 되면서 인터페이스의 모든 메서드를 구현할 필요가 없는 경우에 대한 처리가 편해졌다.
 * 예전에는 인터페이스를 상속받는 abstract 클래스를 만들고 그 안에 내용이 없는 구현 클래스들을 만들어서 상속받는 클래스들이 필요한 메서드만 구현하도록 제공했다.
 */
public class Exec {

    public static void main(String[] args) {
        Foo foo = new DefaultFoo("joonyeop");
        foo.printNameUpperCase();

        FooAndBar fooAndBar = new FooAndBar();
        fooAndBar.printNameUpperCase();

        Foo.printAnything();


        System.out.println("Java API의 기본 메서드와 스태틱 메서드");

        List<String> name = new ArrayList<>();
        name.add("jeff");
        name.add("kim");
        name.add("lee");
        name.add("aida");

        System.out.println("\nfIterable forEach");
        name.forEach(System.out::println);

        System.out.println("\nSpliterator");
        Spliterator<String> spliterator = name.spliterator();
        while (spliterator.tryAdvance(System.out::println));
        System.out.println();

        Spliterator<String> spliterator1 = name.spliterator();
        //반으로 나눔
        Spliterator<String> spliterator2 = spliterator1.trySplit();
        while (spliterator1.tryAdvance(System.out::println));
        System.out.println("============");
        while (spliterator2.tryAdvance(System.out::println));

        System.out.println("\nremoveIf");
        name.removeIf(s -> s.startsWith("a"));
        name.forEach(System.out::println);

        System.out.println("\nComparator");
        name.sort(String::compareToIgnoreCase);
        name.forEach(System.out::println);
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        name.sort(compareToIgnoreCase.reversed());
        name.forEach(System.out::println);
    }
}
