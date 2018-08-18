package cn.jml.pokonyan.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0 created by chenzhenwei_fh on 2018/7/3 16:59
 */
public interface IUserAccessInfoService {

    void saveUserAccessInfo(HttpServletRequest request);
}
