package cn.jml.pokonyan.controller;

import cn.jml.pokonyan.service.UserAccessInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @version 1.0 created by Justmemoryl on 2018/7/3 16:58
 */
@Slf4j
@RestController
public class IndexController {
    @Autowired private UserAccessInfoService userAccessInfoService;

    @RequestMapping("/")
    private ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        userAccessInfoService.saveUserAccessInfo();
        mv.setViewName("index.html");
        return mv;
    }
}
