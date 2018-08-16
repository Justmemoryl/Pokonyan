package cn.jml.pokonyan.remote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @version 1.0 created by chenzhenwei on 2018年6月20日 上午8:57:37
 */
@Data
@ToString
@EqualsAndHashCode
public class AddressDetail {
    /**
     * 城市。
     */
    private String city;
    /**
     * 百度城市代码。
     */
    private String cityCode;
    /**
     * 区县。
     */
    private String district;
    /**
     * 省份。
     */
    private String province;
    /**
     * 街道。
     */
    private String street;
    /**
     * 门牌号。
     */
    private String streetNumber;
}
