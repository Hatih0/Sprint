package itu.hatif.annotation;


import java.lang.annotation.*;;

@Target(ElementType.METHOD)

@Retention(RetentionPolicy.RUNTIME)

public @interface GetUrl {
    String url() default "";
    String method() default "GET";
}
