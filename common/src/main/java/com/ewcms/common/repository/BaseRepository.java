package com.ewcms.common.repository;

import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 抽象DAO层基类 提供一些简便方法<br/>
 * 具体使用请参考测试用例：{@see com.ewcms.common.repository.UserRepository}
 * 想要使用该接口需要在spring配置文件的jpa:repositories中添加
 * factory-class="com.ewcms.common.repository.support.SimpleBaseRepositoryFactoryBean"
 * 泛型 ： M 表示实体类型；ID表示主键类型
 * 
 * @author wu_zhijun
 */
@NoRepositoryBean
public interface BaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID>, JpaSpecificationExecutor<M> {

    /**
     * 根据主键删除
     *
     * @param ids
     */
    public void delete(ID[] ids);
    
    public void delete(List<ID> ids);

    /**
     * 页面查询返回Map
     * 
     * @param searchParameter
     * @return
     */
    public Map<String, Object> query(final SearchParameter<ID> searchParameter);
    
    public Page<M> findAll(final Searchable searchable);
    
    public Long count(final Searchable searchable);
}
