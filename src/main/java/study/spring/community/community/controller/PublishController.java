package study.spring.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.spring.community.community.mapper.QuestionMapper;
import study.spring.community.community.mapper.UserMapper;
import study.spring.community.community.model.Question;
import study.spring.community.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
    Question question = new Question();
    question.setTitle(title);
    question.setDescription(description);
    question.setTag(tag);
    User user;

    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies){
        if (cookie.getName().equals("token")){
            String token = cookie.getValue();
            User user = userMapper.findByToken(token);
            if (user != null){
                request.getSession().setAttribute("user",user);
            }
            break;
        }
    }
    if (user == null){
        model.addAttribute("error","用户未登录");
    }

    questionMapper.create(new Question());
    return "publish";
    }
}
