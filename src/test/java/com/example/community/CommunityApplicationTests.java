package com.example.community;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.dao.UserMapper;
import com.example.community.entity.DiscussPost;
import com.example.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes =CommunityApplication.class)
@MapperScan("com.example.community.dao")
public class CommunityApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @org.junit.Test
    public void testSelectPost(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(0,0,1);
        for(DiscussPost post:list){
            System.out.println(post);
        }
    }
    @org.junit.Test
    public void testSelectPostRows(){
        int rows=discussPostMapper.selectDiscussPostRows(0);
        System.out.println(rows);
    }
    @org.junit.Test
    public void testSelectUser(){
        User user =userMapper.selectById(1);
        User user1=userMapper.selectByEmail("123");
        User user2=userMapper.selectByName("普通创建者阿诚");

        System.out.println(user);
        System.out.println(user1);
        System.out.println(user2);
    }
    @Test
    public void testsertUser(){
        User user=new User();
        user.setUsername("张三");
        user.setPassword("12345");
        user.setSalt("1");
        user.setEmail("123");
        user.setStatus(1);
        user.setType(1);
        user.setActivationCode("123");
        user.setHeaderUrl("123");
        int rows=userMapper.insertUser(user);
    }
    @Test
    public void testUpdateUser(){
        userMapper.updateHeader(1,"123");
        userMapper.updatePassword(2,"123");
        userMapper.updateStatus(3,2);
    }

}
