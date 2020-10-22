package com.example.community.Controller;

import com.example.community.entity.DiscussPost;
import com.example.community.entity.User;
import com.example.community.service.DiscussPostService;
import com.example.community.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/test",method= RequestMethod.GET)
    public String getIndexPage(Model model,@RequestParam(defaultValue = "1",value = "pageNum") Integer pageNum){
        PageHelper.startPage(pageNum,5);
        List<DiscussPost> list=discussPostService.findDicussPosts(123,0,100);
        List<Map<String,Object>> discussPost=new ArrayList<>();
        if (list!=null){
            for(DiscussPost post:list){
                Map<String,Object> map=new HashMap<>();
                map.put("post",post);
                User user=userService.findUserById((int) post.getUserId());
                map.put("user",user);
                discussPost.add(map);
            }
        }
        PageInfo<DiscussPost> pageInfo = new PageInfo<DiscussPost>(list);
        System.out.println(pageInfo);
        System.out.println(discussPost);
        return "/test";
    }

}
