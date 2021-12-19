package optional;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

/*
 * Optional은 메서드의 리턴 타입으로만 사용하자.
 * Optional은 메서드의 매개변수 타입, map의 key, 인스턴스 필드 타입으로 사용하지 말자.
 * 기본 타입 값을 사용해야 한다면 of 보다는 해당 기본 타입용 Optional을 사용. ex) OptionalInt
 * Optional을 리턴하는 경우 null을 리턴하지 말자. (Optional을 사용하는 의미가 없음..)
 * Collection, Map, Stream 등등 비어있는 것을 스스로 표현할 수 있는 컨테이너 타입들에 Optional을 사용하지 말자.
 */
public class Exec {

    public static void main(String[] args) {
        List<OnlineClass> springClasses = new ArrayList<>();
        springClasses.add(new OnlineClass(1, "spring boot", true));
        springClasses.add(new OnlineClass(2, "rest api development", false));

        OnlineClass spring_boot = new OnlineClass(1, "spring boot", true);

        //getProgress가 null인 경우 예외 발생.
        //Duration studyDuration = spring_boot.getProgress().getStudyDuration();

        //optional로 안전하게 사용.
        Optional<Progress> optionalProgress = spring_boot.getProcess();
        if (optionalProgress.isPresent()) {
            Duration studyDuration = optionalProgress.get().getStudyDuration();
        }

        //of에 기본 타입의 값을 사용하면 boxing/unboxing 으로 리소스 사용. (성능 저하)
        Optional.of(10);
        //내부에 변수를 int로 사용하기 때문에 boxing/unboxing 하지 않음.
        OptionalInt.of(10);


        //-------Optional API 사용-------

        Optional<OnlineClass> optionalOnlineClass = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("spring"))
                .findFirst();

        //기본적인 사용 방법이지만 다른 방법들을 더 많이 사용.
        if (optionalOnlineClass.isPresent())
            optionalOnlineClass.get();

        //체크 및 꺼내는 코드 없이 사용.
        optionalOnlineClass.ifPresent(oc -> System.out.println(oc.getTitle()));

        //데이터를 꺼내와야할 때 데이터가 없으면 파라미터로 넘겨준 인수로 꺼내오도록 사용.
        OnlineClass onlineClassByOrElse = springClasses.stream()
                .filter(oc -> oc.getTitle().startsWith("jpa"))
                .findFirst()
                .orElse(createNewClasses());
        System.out.println(onlineClassByOrElse.getTitle());

        //하지만 이런식으로 사용하게 되면 무조건 createNewClases 메서드를 한번 실행해야 함.
        OnlineClass optionalClassByOrElse2 = optionalOnlineClass.orElse(createNewClasses());
        System.out.println(optionalClassByOrElse2.getTitle());

        //orElseGet은 함수형 인터페이스 Supplier를 파라미터로 사용하기 때문에 lazy 하게 사용가능.
        OnlineClass onlineClassByOrElseGet = optionalOnlineClass.orElseGet(Exec::createNewClasses);
        System.out.println(onlineClassByOrElseGet.getTitle());

        /*
         * 이미 만들어져 있는 인스턴스를 사용할 때는 orElse를 사용.
         * 동적으로 작업을 추가해서 만들어내야 하는 경우에는 orElseGet을 사용.
         */

        //데이터가 없으면 예외를 던짐.
        OnlineClass onlineClassByElseThrow = optionalOnlineClass.orElseThrow(IllegalArgumentException::new);

        //값을 한번 걸러내서 통과하면 그대로 반환, 아니면 empty Optional을 반환.
        Optional<OnlineClass> onlineClassByFilter = optionalOnlineClass
                .filter(oc -> !oc.isClosed());

        //Optional이 담게될 데이터 타입을 변환.
        Optional<Integer> integer = optionalOnlineClass.map(OnlineClass::getId);

    }

    private static OnlineClass createNewClasses() {
        System.out.println("creating new online class");
        return new OnlineClass(10, "New class", false);
    }


}
