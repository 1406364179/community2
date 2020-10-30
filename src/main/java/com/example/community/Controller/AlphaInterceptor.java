package com.example.community.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AlphaInterceptor implements HandlerInterceptor {
    private static final Logger logger= LoggerFactory.getLogger(AlphaInterceptor.class);
    //在Controller之前执行

}
