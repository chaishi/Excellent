package edu.swust.cs.excellent.authorized;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 权限注解.
 * 参数需要是 MANA_STU,TEACHER,NOR_STU
 * 中的一种，否则会出异常
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Authority {
    String[] value();
}
