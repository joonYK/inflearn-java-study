package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * 스트림은 연속된 데이터를 처리하는 오퍼레이션의 모음이다.
 * 데이터를 담고 있는 저장소가 아닌 데이터의 흐름(예를 들어 컨베이어 벨트).
 */
public class Exec {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("joonyeop");
        names.add("jeff");
        names.add("foo");
        names.add("bar");
        names.add("baz");

        //Stream으로 전달받은 데이터를 변환하지 않음.
        //기존 데이터는 변화없이 그대로 존재.R
        java.util.stream.Stream<String> stringStream = names.stream().map(String::toUpperCase);
        names.forEach(System.out::println);

        System.out.println();

        //stream은 중개 오퍼레이션와 종료 오퍼레이션가 존재.
        //중개 오퍼레이션은 Stream을 리턴. (lazy. 종료 오퍼레이션이 실행되기 전까지 실행하지 않음)
        //종료 오퍼레이션은 Stream을 리턴하지 않음.

        //중개 오퍼레이션인 map 만으로는 실행되지 않음
        names.stream().map(s -> {
            System.out.println(s);
            return s.toUpperCase();
        });

        System.out.println();

        //종료 오퍼레이터인 collect가 실행되면서 map도 실행
        List<String> collect = names.stream().map(s -> {
            System.out.println(s);
            return s.toUpperCase();
        }).collect(Collectors.toList());

        collect.forEach(System.out::println);

        System.out.println();

        //병렬 처리
        //데이터가 정말 방대한 경우에만 사용하자
        //데이터가 적은 경우 쓰레드를 생성하고 컨텍스트 스위치 등 오히려 성능이 안좋을 수 있음.
        List<String> collect1 = names.parallelStream().map(s -> {
            System.out.println(s + " " + Thread.currentThread().getName());
            return s.toUpperCase();
        }).collect(Collectors.toList());
        collect1.forEach(System.out::println);

    }
}
