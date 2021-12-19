package stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamAPITest {

    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "spring data jpa", true));
        springClasses.add(new OnlineClass(3, "spring mvc", false));
        springClasses.add(new OnlineClass(4, "spring core", false));
        springClasses.add(new OnlineClass(5, "rest api development", false));

        System.out.println("spring 으로 시작하는 수업");
        springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("\nclose 되지 않은 수업");
        springClasses.stream()
                //.filter(oc -> !oc.isClosed())
                .filter(Predicate.not(OnlineClass::isClosed))
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("\n수업 이름만 모아서 스트림 만들기");
        springClasses.stream()
                .map(OnlineClass::getTitle)
                .forEach(System.out::println);


        List<OnlineClass> javaClassees = new ArrayList<>();
        javaClassees.add(new OnlineClass(6, "The Java, Test", true));
        javaClassees.add(new OnlineClass(7, "The Java, Code manipulation", true));
        javaClassees.add(new OnlineClass(8, "The Java, 8 to 11", false));

        List<List<OnlineClass>> joonyeopEvents = new ArrayList<>();
        joonyeopEvents.add(springClasses);
        joonyeopEvents.add(javaClassees);

        System.out.println("\n두 수업 목록에 들어있는 모든 수업 아이디 출력");
        //stream으로 들어온 각각의 OnlineClass 리스트를 한번 더 stream으로 만들어서 onlineClass 스트림으로 만듦
        joonyeopEvents.stream()
                .flatMap(Collection::stream) // list를 다시 stream으로 만들고 합침. 결국 Stream<List<OnlineClass>> -> Stream<OnlineClass>
                .forEach(oc -> System.out.println(oc.getId()));

        System.out.println("\n10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
        Stream.iterate(10, i -> i + 1) //10부터 1씩 증가하는 무제한 스트림
                .skip(10) //10개 빼고
                .limit(10) //10개 까지만
                .forEach(System.out::println);

        System.out.println("\n자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
        boolean test = javaClassees.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
        System.out.println(test);

        System.out.println("\n스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 리스트로 만들기");
        List<String> stringList = springClasses.stream()
                .filter(oc -> oc.getTitle().contains("spring"))
                .map(OnlineClass::getTitle)
                .collect(Collectors.toList());

        stringList.forEach(System.out::println);
    }
}
