package com.github.ybqdren;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Rabbit 配置
 * created by ybqdren
 */
public class RabbitConnFactory {
    private static final String host = "127.0.0.1";
    private static final int port = 5672;
    private static final String username = "ybqdren";
    private static final String password = "ybqdren";
    private static final String virtualHost = "ybqdren";

    private static ConnectionFactory connectionFactory = null;

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
    }

    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = connectionFactory.newConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }



}
