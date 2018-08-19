package cn.jml.pokonyan.repository.mysql.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "UserAccessInfo")
public class UserAccessInfoEntity {
    /**
     * 用户发起请求的外网IP
     */
    @Id
    @Column(name = "ip")
    private String ip;
    /**
     * 省份名称
     */
    @Column(name = "province")
    private String province;
    /**
     * 城市名称
     */
    @Column(name = "city")
    private String city;
    /**
     * 城市的adcode编码
     */
    @Column(name = "adcode")
    private String adcode;
    /**
     * 所在城市矩形区域范围
     */
    @Column(name = "rectangle")
    private String rectangle;
    /**
     * 最近的一次访问时间
     */
    @Column(name = "time")
    private String time;
}
