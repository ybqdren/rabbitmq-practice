package com.github.ybqdren.workqueues;

import com.github.ybqdren.RabbitConnFactory;
import com.github.ybqdren.RabbitConstant;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * created by ybqdren
 */
public class MsgConsumer2 {
    public static void main(String[] args) {
        Connection conn = RabbitConnFactory.getConnection();
        try {
            Channel channel = conn.createChannel();
            channel.queueDeclare(
                    RabbitConstant.QUEUE_NAME,
                    false,
                    false,
                    false,
                    null
            );
            channel.basicConsume(
                    RabbitConstant.QUEUE_NAME,
                    false,
                    new DefaultConsumer(channel){
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                            System.out.println("====消费者2：开始消费====");
                            String msg = new String(body , Charset.forName("UTF-8"));
                            long tagId = envelope.getDeliveryTag();
                            System.out.println("收到消息：消息TagId:【%s】，消息内容：【%s】".formatted(tagId,msg));
                            channel.basicAck(tagId , false);
                        }
                    }
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
