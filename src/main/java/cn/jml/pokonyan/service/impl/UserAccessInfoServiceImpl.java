package cn.jml.pokonyan.service.impl;

import cn.jml.pokonyan.common.utils.DateUtil;
import cn.jml.pokonyan.repository.mysql.UserAccessInfoDao;
import cn.jml.pokonyan.repository.mysql.entity.UserAccessInfoEntity;
import cn.jml.pokonyan.repository.mysql.primary.IPKey;
import cn.jml.pokonyan.service.UserAccessInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import cn.jml.pokonyan.common.utils.LogUtil;
import cn.jml.pokonyan.common.utils.WebUtil;
import cn.jml.pokonyan.remote.LocationInfoService;
import cn.jml.pokonyan.remote.entity.request.LocationInfoRequest;
import cn.jml.pokonyan.remote.entity.response.LocationInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 保存用户访问信息
 *
 * @version 1.0 created by chenzhenwei on 2018年6月19日 下午4:32:20
 */
@Slf4j
@Service
public class UserAccessInfoServiceImpl implements UserAccessInfoService {
    @Autowired
    private UserAccessInfoDao   repository;
    @Autowired
    private LocationInfoService locationInfoService;

    /**
     * 保存用户访问信息到数据库
     */
    @Override public void saveUserAccessInfo() {
        String publicIP = WebUtil.getV4IP();
        String privateIP = WebUtil.getLocalIp();
        LocationInfoResponse locationInfo = getLocationInfoByIP(publicIP);
        UserAccessInfoEntity userAccessInfoEntity = resolveLocationInfo(publicIP, privateIP, locationInfo);
        if (userAccessInfoEntity != null) {
            try {
                repository.save(userAccessInfoEntity);
            } catch (Exception e) {
                LogUtil.error(log, "用户访问信息入库失败，原因：%s", e.getMessage());
            }
        }
    }

    /**
     * 根据用户公网IP地址获取用户详细位置信息
     *
     * @param realIP
     * @return
     */
    private LocationInfoResponse getLocationInfoByIP(String realIP) {
        LocationInfoResponse result = new LocationInfoResponse();
        LocationInfoRequest request = new LocationInfoRequest();
        request.setIp(realIP);
        try {
            result = locationInfoService.getLocationInfoFromBaiduAPI(request);
        } catch (Exception e) {
            LogUtil.error(log, "调用百度地图API失败，原因：%s", e.getMessage());
        }
        return result;
    }

    /**
     * 解析百度地图返回的地址信息，方便入库
     *
     * @param publicIP
     * @param locationInfo
     * @return
     */
    private UserAccessInfoEntity resolveLocationInfo(String publicIP, String localIP, LocationInfoResponse locationInfo) {
        UserAccessInfoEntity result = new UserAccessInfoEntity();
        IPKey primarykey = new IPKey();
        primarykey.setPublicIP(publicIP);
        primarykey.setLocalIP(localIP);
        result.setKey(primarykey);
        if ("0".equals(locationInfo.getStatus())) {
            result.setAddrees(locationInfo.getAddress());
            result.setInsertTime(DateUtil.formatFullStandardDateTime(new Date()));
            // 街道
            String street = locationInfo.getContent().getAddressDetail().getStreet();
            // 门牌号
            String streetNum = locationInfo.getContent().getAddressDetail().getStreetNumber();
            // 省份
            String province = locationInfo.getContent().getAddressDetail().getProvince();
            // 城市
            String city = locationInfo.getContent().getAddressDetail().getCity();
            // 经度
            result.setLongitude(locationInfo.getContent().getPoint().getX());
            // 纬度
            result.setLatitude(locationInfo.getContent().getPoint().getY());
            result.setProvince(locationInfo.getContent().getAddressDetail().getProvince());
            result.setCity(locationInfo.getContent().getAddressDetail().getCity());
            result.setLocationDetail(province + city + street + streetNum);
            return result;
        } else {
            LogUtil.error(log, "调用百度地图API成功，返回码异常 || Code：%s || Description：%s", locationInfo.getStatus(), locationInfo.getMessage());
        }
        return null;
    }
}
