package com.github.ybqdren.pubandsub;

import com.github.ybqdren.RabbitConnFactory;
import com.github.ybqdren.RabbitConstant;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * created by ybqdren
 */
public class Consumer2 {
    public static void main(String[] args) {
        Connection conn = RabbitConnFactory.getConnection();
        try {
            Channel channel = conn.createChannel();
            channel.queueDeclare(
                    RabbitConstant.QUEUE_NAME_PUBANDSUB2,
                    false,
                    false,
                    false,
                    null
            );
            // bind exchange and queues
            channel.queueBind(
                    RabbitConstant.QUEUE_NAME_PUBANDSUB2,
                    RabbitConstant.EXCHANGE_PUBSUB_QUEUE_NAME,
                    ""
            );
            channel.basicQos(1);
            channel.basicConsume(
                    RabbitConstant.QUEUE_NAME_PUBANDSUB2,
                    false,
                    new DefaultConsumer(channel){
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            System.out.println("消费者1接收到消息：%s".formatted(new String(body)));
                            channel.basicAck(envelope.getDeliveryTag() , false);
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
