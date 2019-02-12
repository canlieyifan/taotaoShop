package com.kk.sso.impl;

import com.kk.mapper.UserMapper;
import com.kk.pojo.User;
import com.kk.pojo.UserExample;
import com.kk.redis.JedisClient;
import com.kk.sso.UserService;
import com.kk.utils.E3Result;
import com.kk.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private JedisClient jedisClient;

    @Override
    public E3Result checkUserInfo(String value) {
        String user = jedisClient.get(value);
        if(user==null){
            return E3Result.build(400,"用户信息可能过期,请重新登录");
        }

        jedisClient.expire(value,600);

        return E3Result.ok(JsonUtils.jsonToPojo(user,User.class));

    }

    @Override
    public E3Result checkUserAccount(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);

        if(users.size()==0){
            return E3Result.build(400,"无该用户");
        }

        if(!users.get(0).getPassword().equals(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()))) {
            return E3Result.build(400,"密码错误");
        }


//        将用户信息写入到redis中


        user = users.get(0);
//        安全性考量
        user.setPassword(null);

//        写入到redis中 并设置过期时间十分钟
        jedisClient.set("SESSION:"+user.getId(), JsonUtils.objectToJson(user));
        jedisClient.expire("SESSION:"+user.getId(),600);

//        将token的key带回web端
        return E3Result.ok("SESSION:"+user.getId());
    }

    @Override
    public E3Result register(User user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setEmail(new Date().toString());


        userMapper.insert(user);

        return E3Result.ok("ok");
    }

    @Override
    public E3Result checkData(String value, Integer type) {
//        创建User查询对象
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();

        if(1==type){
            criteria.andUsernameEqualTo(value);
            List<User> users = userMapper.selectByExample(userExample);
            if(users.size()==0||users==null){
                return E3Result.ok(true);
            }

        }else if(2==type){
            criteria.andPhoneEqualTo(value);
            List<User> users = userMapper.selectByExample(userExample);
            if(users.size()==0||users==null){
                return E3Result.ok(true);
            }
        }

        return E3Result.ok(false);
    }
}
