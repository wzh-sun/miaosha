package com.wzhsun.miaosha.service.impl;

import com.wzhsun.miaosha.dao.UserDOMapper;
import com.wzhsun.miaosha.dao.UserPassWordDOMapper;
import com.wzhsun.miaosha.dataobject.UserDO;
import com.wzhsun.miaosha.dataobject.UserPassWordDO;
import com.wzhsun.miaosha.error.BusinessException;
import com.wzhsun.miaosha.error.EmBusinessError;
import com.wzhsun.miaosha.service.UserService;
import com.wzhsun.miaosha.service.model.UserModel;
import com.wzhsun.miaosha.validata.ValidationResult;
import com.wzhsun.miaosha.validata.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPassWordDOMapper userPassWordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        if (userDO == null){
            return null;
        }
        //通过用户id获取对应的用户加密密码信息
        UserPassWordDO userPassWordDO = userPassWordDOMapper.selectByUserId(userDO.getId());

        return convertFromDataObject(userDO,userPassWordDO);
    }

    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
//        if (StringUtils.isEmpty(userModel.getName())
//                || userModel.getGender() == null
//                || userModel.getAge() == null
//                || StringUtils.isEmpty(userModel.getTerphone())){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }

        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //实现model -- dataobject方法
        UserDO userDO = convertFromModel(userModel);
        try{
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException e){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"手机号已经重复注册");
        }

        userModel.setId(userDO.getId());

        UserPassWordDO userPassWordDO = convertPasswordFromModel(userModel);
        userPassWordDOMapper.insertSelective(userPassWordDO);
        return;

    }

    @Override
    public UserModel validatelLogin(String terphone, String encrptPassword) throws BusinessException {
        //通过用户的手机获取用户信息
        UserDO userDO = userDOMapper.selectByTerphone(terphone);
        if(userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        UserPassWordDO userPassWordDO= userPassWordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel= convertFromDataObject(userDO,userPassWordDO);

        //比对用户信息内加密的密码是否和传输进来的密码相匹配
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;

    }

    private UserPassWordDO convertPasswordFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserPassWordDO userPassWordDO = new UserPassWordDO();
        userPassWordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPassWordDO.setUserId(userModel.getId());
        return userPassWordDO;
    }

    private UserDO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPassWordDO userPassWordDO){
        if (userDO == null){
            return  null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if (userPassWordDO != null){
            userModel.setEncrptPassword(userPassWordDO.getEncrptPassword());
        }
        return userModel;
    }
}
