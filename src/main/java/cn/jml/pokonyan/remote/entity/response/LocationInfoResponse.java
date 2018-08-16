package cn.jml.pokonyan.remote.entity.response;

import cn.jml.pokonyan.remote.entity.Content;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by Jml on 2018/6/19 23:08
 **/
@Data
@ToString
@EqualsAndHashCode
public class LocationInfoResponse {
    private String  address;
    private Content content;
    private String  status;
    private String  message;
}
