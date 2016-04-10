package com.ewcms.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 抽象实体基类，提供统一的ID，和相关的基本功能方法
 * 如果是如mysql这种自动生成主键的，请参考{@link BaseEntity}
 * 子类只需要在类头上加 @SequenceGenerator(name="seq", sequenceName="你的sequence名字")
 * 
 * @author wu_zhijun
 */
@MappedSuperclass
public abstract class BaseSequenceEntity<PK extends Serializable> extends AbstractEntity<PK> {

	private static final long serialVersionUID = 1600082205413023731L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private PK id;

    @Override
    public PK getId() {
        return id;
    }

    @Override
    public void setId(PK id) {
        this.id = id;
    }
}
