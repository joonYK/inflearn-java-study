package defaultStaticMethod;

public interface Bar extends Foo {

    //Foo에서 제공하는 default 메서드를 사용하지 못하고 직접 정의하도록 만듦.
    //void printNameUpperCase();

    //Bar에서 default 메서드를 재정의 가능
    default void printNameUpperCase() {
        System.out.println("BAR");
    }

}
