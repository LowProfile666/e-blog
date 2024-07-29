package com.zsm.controller;

import com.zsm.bean.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author : ZSM
 * Time :  2024/07/23
 */
@Controller
public class UserController {
    @GetMapping("/user")
    public String user(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user";
    }
}
