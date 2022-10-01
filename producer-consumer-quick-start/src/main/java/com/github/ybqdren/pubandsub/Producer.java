package com.github.ybqdren.pubandsub;

import com.github.ybqdren.RabbitConnFactory;
import com.github.ybqdren.RabbitConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * created by ybqdren
 */
public class Producer {

    public static void main(String[] args) {
        Connection conn = RabbitConnFactory.getConnection();
        try {
            Channel channel = conn.createChannel();
            String msg = "hello mq";

            // create a exchange 交换机需要提前到平台上创建好
            channel.basicPublish(
                    RabbitConstant.EXCHANGE_PUBSUB_QUEUE_NAME,
                    "",
                    null,
                    msg.getBytes()
            );

            channel.close();
            conn.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
