package defaultStaticMethod;

public interface Foo {

    //기본 메소드
    //인터페이스에 메소드 선언이 아니라 구현체를 제공
    //기본 메소드는 구현체가 몰게 추가되기 때문에 리스크 존재 (@implSpe 자바독 태그로 문서화 필요)
    /**
     * @implSpec 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력.
     */
    default void printNameUpperCase() {
        System.out.println(getName().toUpperCase());
    }

    //해당 타입 관련 헬퍼나 유틸리티 메소드를 제공할 때 인터페이스에 스태틱 메소드 제공
    static void printAnything() {
        System.out.println("Foo");
    }

    String getName();
}
