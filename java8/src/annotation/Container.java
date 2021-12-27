package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//상위 애노테이션은 자기가 감쌀 하위 애노테이션보다 Retention과 Target이 같거나 더 넓어야한다.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Container {

    TypeUseAnnotation[] value();
}
