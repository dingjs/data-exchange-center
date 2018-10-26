package data.exchange.center.monitor.controller;

import com.alibaba.fastjson.JSON;

import data.exchange.center.monitor.security.SecurityUser;
import data.exchange.center.monitor.security.SecurityUtil;
import data.exchange.center.monitor.service.MenuService;

import org.apache.commons.lang3.StringUtils;
import org.bumishi.toolbox.model.RestResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年7月26日 上午11:20:34</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@ControllerAdvice
public class PublicAdvice {
    protected Logger logger= org.slf4j.LoggerFactory.getLogger("bumishi_admin_error_logger");
    @Autowired
    protected MenuService menuService;

    @ExceptionHandler
    public void handleControllerException(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws IOException {
        logger.error("handleControllerException,url:{}",request.getRequestURI(),ex);
        String ajax = request.getHeader("X-Requested-With");
        response.setCharacterEncoding("utf-8");
        if (StringUtils.isBlank(ajax)) {
            response.sendRedirect("/error");
        } else {
            String json= JSON.toJSONString(RestResponse.fail("出错了:" + ex.getMessage()));
            response.setContentType("application/json");
            response.getWriter().println(json);
        }

    }

    @ModelAttribute
    public void addCommonModel(Model model, HttpServletRequest request) {
        SecurityUser user = SecurityUtil.getUser();
        if (user != null) {
            model.addAttribute("user", user);
//            Object list = menuService.getNavMenus(user.getUid());
            model.addAttribute("navs", menuService.getNavMenus(user.getUid()));
        }
    }


}
