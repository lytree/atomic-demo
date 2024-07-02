package top.lytree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Configuration
public class AtomicRedisApplication implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(AtomicRedisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Scheduled(cron = "30 * * * * ?")
    public void Scheduling1() {
        new Thread(() -> redisTemplate.opsForValue().set("test", "string")).start();

    }

    @Scheduled(cron = "30 * * * * ?")
    public void Scheduling2() {
        new Thread(() -> redisTemplate.opsForValue().set("test1", "string")).start();
    }
}
