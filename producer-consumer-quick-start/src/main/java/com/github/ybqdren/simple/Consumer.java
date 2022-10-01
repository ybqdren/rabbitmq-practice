package java.com.github.ybqdren.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 消费者
 * created by ybqdren
 */
public class Consumer {
    public static void main(String[] args) {
        Connection conn = com.github.ybqdren.RabbitConnFactory.getConnection();
        try {
            Channel channel = conn.createChannel();
            channel.queueDeclare(
                    com.github.ybqdren.RabbitConstant.QUEUE_NAME,
                    false,
                    false,
                    false,
                    null
            );
            channel.basicConsume(
                    com.github.ybqdren.RabbitConstant.QUEUE_NAME,
                    false,
                    new Reciver(channel)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

class Reciver extends DefaultConsumer {

    private Channel channel;
    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("====消费者：开始消费====");
        String msg = new String(body , Charset.forName("UTF-8"));
        long tagId = envelope.getDeliveryTag();
        System.out.println("收到消息：消息TagId:【%s】，消息内容：【%s】".formatted(tagId,msg));
        channel.basicAck(tagId , false);
    }
}
