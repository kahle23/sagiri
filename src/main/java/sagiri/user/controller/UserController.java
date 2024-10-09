package sagiri.user.controller;

import com.alibaba.fastjson.JSON;
import kunlun.common.Result;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户相关 前端控制器.
 * @author Sagiri
 * @since 2024-10-07
 */
@Slf4j
@Controller
@RequestMapping("/api")
public class UserController {

    @ResponseBody
    @PostMapping("/admin/login")
    public Result<User> login(@RequestBody User user) {
        log.info("login {}", JSON.toJSONString(user));
        if ("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
            return Result.success(user);
        }
        return Result.failure("用户名或密码错误！");
    }

    @Data
    public static class User {
        private String username;
        private String password;
    }

}
