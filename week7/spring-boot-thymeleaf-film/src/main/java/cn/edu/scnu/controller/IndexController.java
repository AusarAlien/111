package cn.edu.scnu.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String IndexPage(HttpServletRequest request, Model model){
        model.addAttribute("httpServletRequest", request);
        return "index";
    }

    @RequestMapping("/index")
    public String toIndexPage(HttpServletRequest request, Model model){

        model.addAttribute("httpServletRequest", request);
        return "index";
    }

    @RequestMapping("/OrdersView")
    public String OrdersView(HttpServletRequest request, Model model){

        model.addAttribute("httpServletRequest", request);
        return "OrdersView";
    }

    @RequestMapping("/PayView")
    public String PayView(HttpServletRequest request, Model model){

        model.addAttribute("httpServletRequest", request);
        return "PayView";
    }

}
