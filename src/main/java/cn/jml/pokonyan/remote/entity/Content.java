package cn.jml.pokonyan.remote.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @version 1.0 created by chenzhenwei on 2018年6月20日 上午8:56:08
 */
@Data
@ToString
@EqualsAndHashCode
public class Content {
    private String        address;
    private AddressDetail addressDetail;
    private Point         point;
}
