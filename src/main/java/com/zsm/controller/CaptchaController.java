package com.zsm.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Author : ZSM
 * Time :  2024/07/15
 */
@Controller
public class CaptchaController {
    @GetMapping("/codeImage")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建一个长200，宽100，字符个数3，圆圈个数20的验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 3, 20);
        // 获取到生成的验证码
        String code = circleCaptcha.getCode();

        // 要将验证码存入到session中，这样前端输入的验证码可以和session中的验证码进行比较
        request.getSession().setAttribute("CAPTCHA_CODE", code);
        response.setContentType("image/jpeg");

        // 把图片返回给前端
        // 使用ImageIO类，将图片写到响应流中
        // 参数1：图片；参数2：图片格式；参数3：响应流
        ImageIO.write(circleCaptcha.getImage(), "jpeg", response.getOutputStream());
    }
}
