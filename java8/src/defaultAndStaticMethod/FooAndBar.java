package defaultAndStaticMethod;

public class FooAndBar implements Foo, Bar {

    //Foo, Bar 모두 default 메서드가 존재하면 컴파일 에러
    //FooAndBar에서 재정의 하면 해결
    @Override
    public void printNameUpperCase() {
        System.out.println("FooAndBar");
    }

    @Override
    public String getName() {
        return "";
    }
}
