package com.github.ybqdren.workqueues;

import com.github.ybqdren.RabbitConnFactory;
import com.github.ybqdren.RabbitConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by ybqdren
 */
public class MessageProducer {
    public static void main(String[] args) {
        Connection conn = RabbitConnFactory.getConnection();
        Channel channel = null;
        try {
            channel = conn.createChannel();
            // declare a queue
            channel.queueDeclare(
                    RabbitConstant.QUEUE_NAME,
                    false,
                    false,
                    false,
                    null
            );

            // send message
            System.out.println("===== 生产者：开始发送消息====");
            String baseMsg = "hello mq";
            for(int i = 0; i<5000 ;i++){
                System.out.println("消息内容：%s%d".formatted(baseMsg , i));
                channel.basicPublish(
                        "",
                        RabbitConstant.QUEUE_NAME,
                        null,
                        "%s%d".formatted(baseMsg , i).getBytes()
                );
            }
            System.out.println("===== 生产者：消息发送成功====");
            channel.close();
            conn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}

