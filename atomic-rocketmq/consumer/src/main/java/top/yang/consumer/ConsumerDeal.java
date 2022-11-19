package top.yang.consumer;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}", topic = "${rocketmq.consumer.topic}")
public class ConsumerDeal implements RocketMQListener<String> {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerDeal.class);

    @Override
    public void onMessage(String message) {
        logger.info(message);
    }
}
