package study.spring.community.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class ProfileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model){
        if ("question".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName","我的提问");
        }
        return "profile";
    }
}
