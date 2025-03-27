package net.smooth.zhenxiactivity.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeController {
    @GetMapping("/error")
    public String index(Model model) {

        model.addAttribute("name", "Spring Boot");
        return "error";  // 返回的是 error.html
    }

    @RequestMapping("/qwirXNGJlp.txt")
    public String verification() {
        return "qwirXNGJlp.txt";
    }
}
