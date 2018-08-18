package cn.jml.pokonyan.common.utils;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class WebUtil {

    /**
     * 获取Request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }

    /**
     * 用户访问时获取外网IP
     * 
     * @param request
     * @return 用户外网IP
     */
    public static String getOuterNetIp(HttpServletRequest request) {
        String result = "Unknown";
        try {
            result = request.getRemoteAddr();
        } catch (Exception e) {
            LogUtil.error(e, log, "获取外网IP失败 || Cause: %s", e);
        }
        return result;
    }

}
