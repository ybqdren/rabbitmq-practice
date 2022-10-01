package com.github.ybqdren;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 * created by ybqdren
 */
public class Producer {
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
            String msg = "hello mq";
            System.out.println("消息内容：%s".formatted(msg));
            channel.basicPublish(
                    "",
                    RabbitConstant.QUEUE_NAME,
                    null,
                    msg.getBytes()
            );
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
