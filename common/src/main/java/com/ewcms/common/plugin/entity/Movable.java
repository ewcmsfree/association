package com.ewcms.common.plugin.entity;

/**
 * 实体实现该接口表示想要调整数据的顺序
 * 优先级值越大则展示时顺序越靠前 比如 2 排在 1 前边
 * 
 * @author wu_zhijun
 */
public interface Movable {

    public Integer getWeight();

    public void setWeight(Integer weight);

}
