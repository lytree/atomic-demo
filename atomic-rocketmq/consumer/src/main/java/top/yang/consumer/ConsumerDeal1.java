package top.yang.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}", topic = "order-paid-topic")
public class ConsumerDeal1 implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("deal1" + message);
    }
}
