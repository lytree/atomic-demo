package top.yang.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author PrideYang
 */
@Controller
@RequestMapping("/index")
public class IndexController {


    @PostMapping("/{dictCodes}")
    public void remove(@PathVariable Long[] dictCodes) {
        System.out.println("----------------");
    }
}
