package top.yang.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String json = "异步消息";
        rocketMQTemplate.asyncSend("order-paid-topic", json, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("123");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("456");
            }
        }, 10000);
    }
}
