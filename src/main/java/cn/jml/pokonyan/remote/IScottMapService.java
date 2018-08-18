package cn.jml.pokonyan.remote;

import cn.jml.pokonyan.remote.entity.request.ScottMapRequest;
import cn.jml.pokonyan.remote.entity.response.ScottMapResponse;

import org.springframework.stereotype.Component;

/**
 * Created by Jml on 2018/6/19 23:06
 **/
@Component
public interface IScottMapService {
    /**
     * 向指定URL(高德地图API)发送GET请求，根据IP地址获取详细地理位置信息
     *
     * @return
     */
    ScottMapResponse getLocationInfoByIP(ScottMapRequest request);
}
