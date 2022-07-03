package com.practice.rocketmqdemo;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
class RocketMqDemoApplicationTests {

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Test
    void contextLoads() {
        rocketMQTemplate.convertAndSend("TestTopic","secondMessage");
    }

}
