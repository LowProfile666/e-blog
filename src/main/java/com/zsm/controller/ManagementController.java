package com.zsm.controller;

import com.zsm.bean.Article;
import com.zsm.bean.User;
import com.zsm.service.ArticleService;
import com.zsm.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


/**
 * Author : ZSM
 * Time :  2024/06/29
 */
@Controller
public class ManagementController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/login")
    public String login(HttpSession session) {
        session.setAttribute("captchaCodeErrorMsg", "");
        session.setAttribute("message", "");
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, @RequestParam("code") String code, HttpSession session) {
        // 清除提示信息
        session.setAttribute("captchaCodeErrorMsg", "");
        session.setAttribute("message", "");

        // 获取用户输入的验证码
        if (code == null || code.isEmpty()) {
            session.setAttribute("captchaCodeErrorMsg", "验证码不能为空");
            return "login";
        }

        // 获取session中的验证码
        String captchaCode = (String) session.getAttribute("CAPTCHA_CODE");
        if (captchaCode == null || captchaCode.isEmpty()) {
            session.setAttribute("captchaCodeErrorMsg", "验证码错误");
            return "login";
        }

        // 判断是否相等
        if (!code.equalsIgnoreCase(captchaCode)) {
            session.setAttribute("captchaCodeErrorMsg", "验证码不正确");
            return "login";
        }

        // 验证码只能使用一次，删除session中的验证码
        session.removeAttribute("CAPTCHA_CODE");
        user = userService.check(user);
        if (user != null) {
            session.setAttribute("user", user);
            String originalUrl = (String) session.getAttribute("originalUrl");
            session.removeAttribute("originalUrl");
            session.removeAttribute("message");
            return "redirect:" + (originalUrl == null ? "/" : originalUrl);
        }

        session.setAttribute("message", "登录失败！账号或密码错误！");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("data", new Article());
        return "edit";
    }

    @PostMapping("/add")
    public String add(@RequestParam("title") String title,
                      @RequestParam(value = "photo", required = false) MultipartFile photo,
                      @RequestParam("desc") String desc,
                      @RequestParam("content") String content,
                      @RequestParam("date") String date,
                      @RequestParam("type") String type,
                      @RequestParam(value = "id", required = false) Integer id,
                      @RequestParam(value = "photoPath", required = false) String photoPath,
                      HttpServletRequest request) throws Exception {

        if (photo == null) {
            if (photoPath == null || photoPath.isEmpty())
                photoPath = "/static/images/img1.png";
        } else {
            // 获取文件的真实名字
            String fileName = photo.getOriginalFilename();
            // 获取到服务器根目录下的upload目录
            String uploadDir = request.getServletContext().getRealPath("/upload");
            File uploadDirFile = new File(uploadDir);
            // 没有该目录就创建
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // 定义存储路径
            String filePath = uploadDir + File.separator + fileName;
            File destFile = new File(filePath);

            // 保存文件
            photo.transferTo(destFile);

            // 生成URL路径
            photoPath = request.getContextPath() + "/upload/" + fileName;
        }
        Article article = null;

        if (id == null) {
            article = new Article(id, title, desc, photoPath, content, date, null, type);
            articleService.add(article);
        } else {
            article = new Article(id, title, desc, photoPath, content, null, date, type);
            articleService.edit(article);
        }

        return "management";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap modelMap) {
        Article article = articleService.getById(id);
        modelMap.addAttribute("data", article);
        modelMap.addAttribute("type", "edit");
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, HttpServletRequest request) {
        articleService.delete(id);
        return "redirect:/management";
    }
}
