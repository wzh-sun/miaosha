package com.wzhsun.miaosha.service;

import com.wzhsun.miaosha.error.BusinessException;
import com.wzhsun.miaosha.service.model.UserModel;

public interface UserService {
    //通过用户id获取用户对象的方法
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

    //terphone 是用户注册的手机 password是用户加密后的密码
    UserModel validatelLogin(String terphone,String encrptPassword) throws BusinessException;
}
