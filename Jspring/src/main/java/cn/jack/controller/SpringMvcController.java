package cn.jack.controller;

import cn.jack.annotation.Jcontroller;
import cn.jack.annotation.JrequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: ZhangXing
 * @Date: 2019/1/6 * @Description: cn.jack.controller * @version: 1.0
 */
@Jcontroller
@JrequestMapping("/controller")
public class SpringMvcController {


    @JrequestMapping("/testMvc")
    public String testMvc(){
    System.out.println("哈哈哈哈");
        return "success";
    }
    @JrequestMapping("/testres")
    public void testRes(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("doTest2 method success!");;

    }
}
