package cn.jml.pokonyan.repository.mysql.entity;

import cn.jml.pokonyan.repository.mysql.primary.IPKey;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "UserAccessInfo")
public class UserAccessInfoEntity {
    @EmbeddedId
    private IPKey  key;
    /**
     * 经度
     */
    @Column(name = "longitude")
    private String longitude;
    /**
     * 纬度
     */
    @Column(name = "latitude")
    private String latitude;
    /**
     * 省份
     */
    @Column(name = "province")
    private String province;
    /**
     * 城市
     */
    @Column(name = "city")
    private String city;
    /**
     * 地理位置详情
     */
    @Column(name = "locationDetail")
    private String locationDetail;
    /**
     * 位置及网络详情
     */
    @Column(name = "addrees")
    private String addrees;
    /**
     * 插入时间
     */
    @Column(name = "insertTime")
    private String insertTime;
}
