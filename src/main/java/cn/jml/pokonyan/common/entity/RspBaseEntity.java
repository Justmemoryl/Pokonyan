package cn.jml.pokonyan.common.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class RspBaseEntity implements Serializable {
    private static final long serialVersionUID = 549597423164032622L;
    private String            returnCode;
    private String            returnDesc;
}
