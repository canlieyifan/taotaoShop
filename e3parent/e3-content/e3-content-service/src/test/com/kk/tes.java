package com.kk;

import com.kk.redis.JedisClient;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class tes {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
        JedisClient jedispool = (JedisClient) context.getBean("clientCluster");
        jedispool.set("1","12");
        String va = jedispool.get("1");
        System.out.println("va = " + va);
    }
}
