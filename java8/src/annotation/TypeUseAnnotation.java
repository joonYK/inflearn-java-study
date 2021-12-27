package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE) // TYPE_PARAMETER를 포함해서 Type을 선언하는 모든곳에 사용 가능
@Repeatable(Container.class) //같은 애노테이션을 반복해서 사용 가능. 그 방법으로 이 애노테이션을 감싸는 상위 애노테이션을 지정해야 한다.
public @interface TypeUseAnnotation {
    String value() default "";
}
