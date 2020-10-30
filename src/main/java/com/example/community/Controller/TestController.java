package com.example.community.Controller;

import com.example.community.until.CommunityUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {
    @RequestMapping("hello")
    public String hello(){
        return "Ss";
    }

    @RequestMapping("/http")
    public void http(){

    }
    //强制只能get请求访问method = RequestMethod.GET
    @RequestMapping(path = "/students",method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam (name="current",required = false,defaultValue = "1") int current, int limit){
        return  "some";
    }
    // /student/123
    // 当获取的内容直接通过地址得到内容时
    //@PathVariable  路径变量
    @RequestMapping(path="/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "123";
    }

    //响应html数据
    //返回html，不加  @ResponseBody ，默认返回HTML

    @RequestMapping(path="/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav =new ModelAndView();
        mav.addObject("name","张三");
        return mav;
    }
    //cookie示例
    @RequestMapping(path = "/cookie/set",method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        //创建cookie
        //只能存字符串，并且一个只能存一个
        Cookie cookie=new Cookie("code", CommunityUtil.generateUUID());
        //设置cookie生效的范围,只有此路径或者是其子路径下
        cookie.setPath("");
        //设置cookie生存时间，通常cookie存在浏览器内存中，关掉浏览器就删除了，但是可以设定时间
        cookie.setMaxAge(60*10);//十分钟
        //发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }
    @RequestMapping(path = "/cookie/get",method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code")String code){
        System.out.println(code);
        return "get cookie";
    }

    //session示例,保存在服务端，一般通过cookieid获取
    @RequestMapping(path = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","Test");
        return "set Session";
    }
    @RequestMapping(path = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get Session";
    }

}
