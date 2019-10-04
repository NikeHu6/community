package com.nicode.community.community.service;

import com.nicode.community.community.mapper.UserMapper;
import com.nicode.community.community.model.User;
import com.nicode.community.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() != 0){
            User dbUser = users.get(0);
            User record = new User();
            record.setGmtModified(System.currentTimeMillis());
            record.setToken(user.getToken());
            record.setAvatarUrl(user.getAvatarUrl());
            record.setName(user.getName());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(record, example);
        }else{
            //把user插入到数据库
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
