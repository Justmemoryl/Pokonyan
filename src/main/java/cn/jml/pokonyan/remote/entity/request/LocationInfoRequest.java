package cn.jml.pokonyan.remote.entity.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Jml on 2018/6/19 23:06
 **/
@Data
@ToString
@EqualsAndHashCode
public class LocationInfoRequest {
    /**
     * 用户请求IP地址
     */
    private String ip;
}
