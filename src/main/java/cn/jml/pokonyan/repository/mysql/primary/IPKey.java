package cn.jml.pokonyan.repository.mysql.primary;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @version 1.0 created by chenzhenwei_fh on 2018/7/3 15:50
 */
@Data
@ToString
@Embeddable
@EqualsAndHashCode(callSuper = false)
public class IPKey implements Serializable {
    /**
     * 公网IP
     */
    @Column(name = "publicIP")
    private String publicIP;
    /**
     * 内网IP
     */
    @Column(name = "localIP")
    private String localIP;
}
