package com.jupiter.tools.spring.test.postgres.customizer;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface NewPgTc {

    String value() default "";
}
