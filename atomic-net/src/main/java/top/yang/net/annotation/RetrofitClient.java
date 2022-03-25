package top.yang.net.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitClient {

    String baseUrl() default "";

    /**
     * @return validateEagerly
     */
    boolean validateEagerly() default false;

    Class<? extends CallAdapter.Factory>[] callAdapter() default {};

    Class<? extends Converter.Factory>[] converter() default {JacksonConverterFactory.class};
}
