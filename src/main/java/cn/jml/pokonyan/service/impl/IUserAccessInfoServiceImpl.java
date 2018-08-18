package cn.jml.pokonyan.service.impl;

import cn.jml.pokonyan.common.constants.Constants;
import cn.jml.pokonyan.common.utils.DateUtil;
import cn.jml.pokonyan.repository.mysql.UserAccessInfoDao;
import cn.jml.pokonyan.repository.mysql.entity.UserAccessInfoEntity;
import cn.jml.pokonyan.service.IUserAccessInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import cn.jml.pokonyan.common.utils.LogUtil;
import cn.jml.pokonyan.common.utils.WebUtil;
import cn.jml.pokonyan.remote.IScottMapService;
import cn.jml.pokonyan.remote.entity.request.ScottMapRequest;
import cn.jml.pokonyan.remote.entity.response.ScottMapResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 保存用户访问信息
 *
 * @version 1.0 created by chenzhenwei on 2018年6月19日 下午4:32:20
 */
@Slf4j
@Service
public class IUserAccessInfoServiceImpl implements IUserAccessInfoService {
    @Autowired
    private UserAccessInfoDao repository;
    @Autowired
    private IScottMapService  IScottMapService;

    /**
     * 保存用户访问信息到数据库
     */
    @Override
    public void saveUserAccessInfo(HttpServletRequest request) {
        String ip = WebUtil.getOuterNetIp(request);
        ScottMapResponse locationInfo = getLocationInfoByIPFromScottMap(ip);
        UserAccessInfoEntity userAccessInfoEntity = resolveLocationInfo(ip, locationInfo);
        if (userAccessInfoEntity != null) {
            try {
                repository.save(userAccessInfoEntity);
            } catch (Exception e) {
                LogUtil.error(e, log, "用户访问信息入库失败，原因：%s", e.getMessage());
            }
        }
    }

    /**
     * 根据用户公网IP地址获取用户详细位置信息
     *
     * @param ip
     *            用户外网IP
     * @return
     */
    private ScottMapResponse getLocationInfoByIPFromScottMap(String ip) {
        ScottMapResponse result = new ScottMapResponse();
        ScottMapRequest request = new ScottMapRequest();
        request.setIp(ip);
        request.setKey(Constants.SCOTTMAP_API_KEY);
        request.setOutput("json");
        try {
            result = IScottMapService.getLocationInfoByIP(request);
        } catch (Exception e) {
            LogUtil.error(e, log, "调用高德地图API失败，原因：%s", e.getMessage());
        }
        return result;
    }

    /**
     * 解析高德地图返回的地址信息，方便入库
     *
     * @param ip
     *            用户外网IP
     * @param locationInfo
     *            高德地图返回的地址信息
     * @return
     */
    private UserAccessInfoEntity resolveLocationInfo(String ip, ScottMapResponse locationInfo) {
        UserAccessInfoEntity result = new UserAccessInfoEntity();
        result.setIp(ip);
        if ("1".equals(locationInfo.getStatus())) {
            result.setProvince(locationInfo.getProvince());
            result.setCity(locationInfo.getCity());
            result.setAdcode(locationInfo.getAdcode());
            result.setRectangle(locationInfo.getRectangle());
            result.setTime(DateUtil.formatFullStandardDateTime(new Date()));
            return result;
        } else {
            LogUtil.error(log, "调用高德地图API成功，返回码异常 || Code：%s || Info：%s || InfoCode: %s", locationInfo.getStatus(), locationInfo.getInfo(),
                locationInfo.getInfocode());
        }
        return null;
    }
}
