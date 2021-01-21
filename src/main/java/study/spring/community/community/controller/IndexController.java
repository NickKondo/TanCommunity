package study.spring.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller//允许类接受前端请求
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";//自动找hello模板
    }

}


