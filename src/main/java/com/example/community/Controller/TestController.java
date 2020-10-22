package com.example.community.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

}
