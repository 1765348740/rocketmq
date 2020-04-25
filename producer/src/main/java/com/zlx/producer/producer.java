package com.zlx.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;


/**
 * @author 薛定谔的猫
 * @Classname producer
 * @Description TODO
 * @Date 2020/4/25 13:10
 */
public class producer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("s2");
        producer.setNamesrvAddr("121.199.21.87:9876");
        producer.start();

        Message msg = new Message();
        msg.setTopic("mytopic");
        msg.setTags("mytag");
        msg.setBody("mybody".getBytes());

        try {
            producer.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
