package com.kk;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/*.xml")
public class AppTest
{


    @Test
    public  void  fun1(){

    }







    /*
        发送队列消息
     */
    @Test
    public void sendQueueMessage() throws Exception {
//        获取连接工厂对象/连接对象
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.80.130:61616");
        Connection connection = factory.createConnection();

//        开启连接对象
        connection.start();

        /*
             创建session对象
             参数1 : 事务是否开启 true则第二个参数无效
             参数2 : 应答模式 1自动应答 ...
         */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        /*
            创建消息类型 (queue消息队列, topic广播)
         */
        Queue queue = session.createQueue("test_queue");

//        根据参数类型 提供生产者
        MessageProducer producer = session.createProducer(queue);

//        创建消息
        TextMessage message = session.createTextMessage("我操你妈 你妈了个逼的");

//        生产者发生消息
        producer.send(message);

//        关闭资源
        producer.close();
        session.close();
        connection.close();
    }


    /*
        接受队列消息
     */
    @Test
    public void receiveQueueMessage()throws Exception{
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.80.130:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("test_queue");
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("text = " + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();

    }


    /*
        发生广播信息
     */
    @Test
    public void sendTopicMessage()throws Exception{
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.80.130:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 创建广播类型
        Topic topic = session.createTopic("test_topic");

        MessageProducer producer = session.createProducer(topic);

        TextMessage message = session.createTextMessage("这是高手 这个厉害");

        producer.send(message);

        producer.close();
        session.close();
        connection.close();
    }



//    接受广播信息
    @Test
    public  void receiveTopicMessage()throws Exception{
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.80.130:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("test_topic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("text = " + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });


        System.in.read();

    }





}








