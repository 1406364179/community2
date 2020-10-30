package com.example.community;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.dao.LoginTicketMapper;
import com.example.community.dao.UserMapper;
import com.example.community.entity.DiscussPost;
import com.example.community.entity.LoginTicket;
import com.example.community.entity.User;
import com.example.community.until.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.sql.Date;
import java.sql.Timestamp;
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

    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void testTextMail(){
        mailClient.sendMail("1406364179@qq.com","Test","内容");
    }
    @Test
    public void testHtmlMail(){
        Context context=new Context();
        context.setVariable("username","lvjiacheng");
        String content=templateEngine.process("/mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("1406364179@qq.com","Html测试",content);
    }

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Timestamp(System.currentTimeMillis()+1000*60*10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket=loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc",1);
        loginTicket=loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);
    }
}
