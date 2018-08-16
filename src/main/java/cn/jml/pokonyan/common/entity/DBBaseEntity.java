package cn.jml.pokonyan.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

/**
 * MongoDB实体基类，自动定义ID，数据插入时间和数据最后修改时间
 * 
 * @version 1.0 created by chenzhenwei on 2018年6月19日 上午10:19:50
 */
@Data
@EntityListeners(AuditingEntityListener.class)
public class DBBaseEntity<ID extends Serializable> implements Persistable {
    public static final long serialVersionUID = -1798386718096394690L;
    @Id
    private ID                id;
    @CreatedDate
    private Date              insertTime;
    @LastModifiedDate
    private Date              lastUpdateTime;

    @Override
    public boolean isNew() {
        return id == null || "".equals(id);
    }

}
