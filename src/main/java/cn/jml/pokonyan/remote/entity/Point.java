package cn.jml.pokonyan.remote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @version 1.0 created by chenzhenwei on 2018年6月20日 上午8:58:16
 */
@Data
@ToString
@EqualsAndHashCode
public class Point {
    /**
     * 当前城市中心点经度。
     */
    private String x;
    /**
     * 当前城市中心点纬度。
     */
    private String y;
}
