package com.kk.sso;

import com.kk.pojo.User;
import com.kk.utils.E3Result;

public interface UserService {
    E3Result checkData(String value,Integer type);
    E3Result register(User user);
}
