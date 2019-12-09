package com.wzhsun.miaosha.dao;

import com.wzhsun.miaosha.dataobject.UserPassWordDO;

public interface UserPassWordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassWordDO record);

    int insertSelective(UserPassWordDO record);

    UserPassWordDO selectByPrimaryKey(Integer id);

    UserPassWordDO selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPassWordDO record);

    int updateByPrimaryKey(UserPassWordDO record);
}