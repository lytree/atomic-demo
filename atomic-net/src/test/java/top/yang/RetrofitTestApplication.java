package top.yang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import retrofit2.converter.jackson.JacksonConverterFactory;

@SpringBootApplication
public class RetrofitTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrofitTestApplication.class, args);
    }

    @Bean
    JacksonConverterFactory gsonConverterFactory() {
        return JacksonConverterFactory.create();
    }


}
