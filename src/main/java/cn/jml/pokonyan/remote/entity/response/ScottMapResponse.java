package cn.jml.pokonyan.remote.entity.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Jml on 2018/6/19 23:08
 **/
@Data
@ToString
@EqualsAndHashCode
public class ScottMapResponse {
    /**
     * 返回结果状态值
     */
    private String status;
    /**
     * 返回状态说明
     */
    private String info;
    /**
     * 状态码
     */
    private String infocode;
    /**
     * 省份名称
     */
    private String province;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 城市的adcode编码
     */
    private String adcode;
    /**
     * 所在城市矩形区域范围(左下右上)
     */
    private String rectangle;
}
