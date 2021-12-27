package annotation;

import java.util.Arrays;
import java.util.List;

/**
 * 자바 8로 들어오면서 애노테이션을 여러 타입에 선언 가능. (제네릭 타입, 변수 타입, 매개변수 타입, 예외 타입)
 * 반복적으로 선언 가능. (컨테이너 애노테이션 필요)
 */
@TypeUseAnnotation("TypeUse1")
@TypeUseAnnotation("TypeUse2")
public class Exec {

    public static void main(@TypeUseAnnotation String[] args) throws @TypeUseAnnotation RuntimeException {
        List<@TypeUseAnnotation String> names = Arrays.asList("joonyeop");


        //같은 애노테이션 반복 사용 예.
        TypeUseAnnotation[] annotationsByType = Exec.class.getAnnotationsByType(TypeUseAnnotation.class);
        for (TypeUseAnnotation typeUseAnnotation : annotationsByType) {
            System.out.println(typeUseAnnotation.value());
        }

        Container container = Exec.class.getAnnotation(Container.class);
        for (TypeUseAnnotation typeUseAnnotation : container.value()) {
            System.out.println(typeUseAnnotation.value());
        }
    }

    static class TypeParamClass<@TypeParamAnnotation T> {

        public static <@TypeParamAnnotation C> void print(C c) {
            System.out.println(c);
        }
    }

    static class TypeUseClass<@TypeUseAnnotation T> {

        public static <@TypeParamAnnotation C> void print(@TypeUseAnnotation C c) {
            System.out.println(c);
        }
    }
}
