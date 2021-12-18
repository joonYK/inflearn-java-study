package defaultStaticMethod;

public class DefaultFoo implements Foo {

    private String name;

    public DefaultFoo(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
