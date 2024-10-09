package sagiri.page.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 后台管理页面 前端控制器.
 * @author Sagiri
 * @since 2024-10-07
 */
@Slf4j
@Controller
public class AdminController {

    /**
     * 登录页面.
     * @return 要渲染的模板名称
     */
    @GetMapping("/admin/login")
    public String login() {

        return "admin/login.html";
    }

    /**
     * 后台首页.
     * @return 要渲染的模板名称
     */
    @GetMapping("/admin/index")
    public String index() {

        return "admin/index.html";
    }

    @GetMapping("/admin/welcome")
    public String welcome() {

        return "admin/welcome.html";
    }

    @GetMapping("/admin/article/list")
    public String articleList() {

        return "admin/article/list.html";
    }

    @GetMapping("/admin/article/category-list")
    public String articleCategoryList() {

        return "admin/article/category-list.html";
    }

    @GetMapping("/admin/file/list")
    public String instrument() {

        return "admin/file/list.html";
    }

}
