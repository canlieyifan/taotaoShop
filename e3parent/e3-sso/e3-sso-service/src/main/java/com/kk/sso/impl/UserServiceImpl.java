package com.kk.sso.impl;

import com.kk.mapper.UserMapper;
import com.kk.pojo.User;
import com.kk.pojo.UserExample;
import com.kk.sso.UserService;
import com.kk.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

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
