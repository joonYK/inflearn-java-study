package functional.lambda;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class Foo {

    public static void main(String[] args) {
        Foo foo = new Foo();
        foo.run();
    }

    private void run() {
        //final 이란 키워드가 없어도 아래의 3가지 방법으로 참조 가능.
        //하지만 람다는 쉐도잉이 되지 않는 차이점이 있음. (scope가 다름)
        //로컬 클래스나 익명 클래스에 선언한 변수가 baseNumber 이라는 이름으로 같으면 밖의 baseNumber 변수가 가려짐.
        int baseNumber = 10;

        //로컬 클래스
        //또 하나의 scope을 가짐
        class LocalClass {
            void printBaseNumber() {
                int baseNumber = 11;
                System.out.println(baseNumber);
            }
        }

        LocalClass localClass = new LocalClass();
        localClass.printBaseNumber();

        //익명 클래스
        //또 하나의 scope을 가짐.
        Consumer<Integer> integerConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer baseNumber) {
                baseNumber = 11;
                System.out.println(baseNumber);
            }
        };

        integerConsumer.accept(baseNumber);

        //람다
        //run 메서드와 scope이 같음.
        //baseNumber이라는 동일한 이름의 변수 선언 불가.
        //IntConsumer printInt = baseNumber -> System.out.println(baseNumber + 10);
        //그리고 baseNumber를 사실상 final로 생각하고 사용하고 있기 때문에 뒤에서 baseNumber를 변경하려 하면 에러 발생.
        IntConsumer printInt = i -> System.out.println(i + 10);
        printInt.accept(10);
    }
}
