package cn.jml.pokonyan.remote;

import cn.jml.pokonyan.remote.entity.request.LocationInfoRequest;
import cn.jml.pokonyan.remote.entity.response.LocationInfoResponse;

import org.springframework.stereotype.Component;

/**
 * Created by Jml on 2018/6/19 23:06
 **/
@Component
public interface LocationInfoService {
    /**
     * 向指定URL(百度地图API)发送GET请求，根据IP地址获取详细地理位置信息
     *
     * @return
     */
    LocationInfoResponse getLocationInfoFromBaiduAPI(LocationInfoRequest request);
}
