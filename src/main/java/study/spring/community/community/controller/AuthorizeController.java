package study.spring.community.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.spring.community.community.dto.AccessTokenDTO;
import study.spring.community.community.dto.GithubUser;
import study.spring.community.community.mapper.UserMapper;
import study.spring.community.community.model.User;
import study.spring.community.community.provider.GitHubProvider;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController<response> {
    @Autowired//将Spring容器中写好的实例加载到上下文
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper; //通过注解预先把对象实例化好，然后注入到容器中

    @GetMapping("/callback")//定义一个方法返回到页面
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request
                           ) {
        String result = "redirect:index";
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_id("c65e7c642856c34111df");
        accessTokenDTO.setClient_secret("b4d5d5463c065e43b4df9cc1cd05dcdcf4a113a8");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        gitHubProvider.getAccessToken(accessTokenDTO);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = (GithubUser) gitHubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());//UUID
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
           // user.setAvatarUrl();
            userMapper.insert(user);
          // response.addCookie(new Cookie("token", token));
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";


            //登录成功，写cookie和session

        } else {
            return "redirect:/";
            //登录失败，重新登录
        }
        //登陆成功后返回index页面

    }
}
