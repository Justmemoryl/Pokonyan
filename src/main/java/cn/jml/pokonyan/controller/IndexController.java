package cn.jml.pokonyan.controller;

import cn.jml.pokonyan.service.IUserAccessInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0 created by Justmemoryl on 2018/7/3 16:58
 */
@Slf4j
@RestController
public class IndexController {
    @Autowired
    private IUserAccessInfoService userAccessInfoService;

    @RequestMapping(value = "/")
    private ModelAndView index(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        userAccessInfoService.saveUserAccessInfo(request);
        mv.setViewName("index.html");
        return mv;
    }
}
