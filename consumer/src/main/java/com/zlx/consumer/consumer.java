package com.zlx.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author 薛定谔的猫
 * @Classname consumer
 * @Description TODO
 * @Date 2020/4/25 13:09
 */
public class consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("s1");
        consumer.setNamesrvAddr("121.199.21.87:9876");
        try {
            consumer.subscribe("mytopic", "mytag");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for(int i=0; i<msgs.size(); i++){
                    MessageExt msg = msgs.get(i);
                    System.out.println(msg.getTopic() + " " + msg.getTags() + " " + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();

    }
}
