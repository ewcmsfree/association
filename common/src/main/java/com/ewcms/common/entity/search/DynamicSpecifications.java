package com.ewcms.common.entity.search;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.ewcms.common.entity.search.filter.AndCondition;
import com.ewcms.common.entity.search.filter.Condition;
import com.ewcms.common.entity.search.filter.OrCondition;
import com.ewcms.common.entity.search.filter.SearchFilter;
import com.ewcms.common.utils.EmptyUtil;
import com.google.common.collect.Lists;

public final class DynamicSpecifications {
	
	public static <T> Specification<T> bySearchFilter(final Searchable searchable, final Class<T> clazz){
		return new Specification<T>(){
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (!searchable.hasSearchFilter()) {
		            return null;
		        }
				List<Predicate> predicates = Lists.newLinkedList();
				for (SearchFilter searchFilter : searchable.getSearchFilters()) {
		            if (searchFilter instanceof Condition) {
		                Condition condition = (Condition) searchFilter;
		                Predicate andPredicate = generatePredicate(condition, root, builder);
		            	predicates.add(andPredicate);
		            } else if (searchFilter instanceof OrCondition) {
		            	List<SearchFilter> orSearchFilters = ((OrCondition) searchFilter).getOrFilters();
		            	Predicate orPredicate = bySearchFilterUseOrCondition(orSearchFilters, clazz).toPredicate(root, query, builder);
		            	predicates.add(orPredicate);
		            } else if (searchFilter instanceof AndCondition) {
		            	List<SearchFilter> andSearchFilters = ((AndCondition) searchFilter).getAndFilters();
		            	Predicate andPredicate = bySearchFilterUseAndCondition(andSearchFilters, clazz).toPredicate(root, query, builder);
		            	predicates.add(andPredicate);
		            }
				}
				query.distinct(true);
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return query.getRestriction();
			}
		};
	}
	
	
	
	/**
	 * 所有查询条件之间用 or 
	 * 
	 * @param orSearchFilters 
	 * @param clazz
	 * @return
	 */
	private static <T> Specification<T> bySearchFilterUseOrCondition(final Collection<SearchFilter> orSearchFilters, final Class<T> clazz){
		return new Specification<T>(){
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (EmptyUtil.isCollectionNotEmpty(orSearchFilters)) {
					
					List<Predicate> orPredicates = generatePredicates(orSearchFilters, root, builder);
					
					//将所有条件用 or 联合起来
					if (orPredicates.size() > 0){
						return builder.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
					}
				}
				return builder.conjunction();
			}
		};
	}
	
	/**
	 * 所有条件之间使用 and
	 * 
	 * @param andSearchFilters
	 * @param clazz
	 * @return
	 */
	private static <T> Specification<T> bySearchFilterUseAndCondition(final Collection<SearchFilter> andSearchFilters, final Class<T> clazz) {
		return new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (EmptyUtil.isCollectionNotEmpty(andSearchFilters)) {
					
					List<Predicate> andPredicates = generatePredicates(andSearchFilters, root, builder);

					// 将所有条件用 and 联合起来
					if (andPredicates.size() > 0) {
						return builder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
					}
				}
				return builder.conjunction();
			}
		};
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> Predicate generatePredicate(final Condition condition, Root<T> root, CriteriaBuilder builder){
		// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
		// 集合对象中间用"|"来隔开，如在Archive对象中有organizations的List<Organization>的集合, 则在前台定义为organizations|id
		
		String searchProperty = condition.getSearchProperty();
		
		String[] names = null;
		Path expression = null;
		if (searchProperty.indexOf("|") > 0){
			names = StringUtils.split(searchProperty, "|");
			try{
				//字段为集合类时使用
				expression = root.join(names[0]);
			}catch (Exception e){
				expression = root.get(names[0]);
			}
		} else {
			names = StringUtils.split(searchProperty, ".");
			try{
				expression = root.get(names[0]);
			}catch (Exception e){
				//字段为集合类时使用
				expression = root.join(names[0]);
			}
		}
		
		for (int i = 1; i < names.length; i++) {
			expression = expression.get(names[i]);
		}
		
		// logic operator
		switch (condition.getOperator()) {
		case EQ:
			return builder.equal(expression, condition.getValue());
		case NE:
			return builder.notEqual(expression, condition.getValue());
		case GT:
			return builder.greaterThan(expression, (Comparable) condition.getValue());
		case GTE:
			return builder.greaterThanOrEqualTo(expression, (Comparable) condition.getValue());
		case LT:
			return builder.lessThan(expression, (Comparable) condition.getValue());
		case LTE:
			return builder.lessThanOrEqualTo(expression, (Comparable) condition.getValue());
		case PREFIXLIKE :
			return builder.like(expression, condition.getValue() + "%");
		case PREFIXNOTLIKE :
			return builder.notLike(expression, condition.getValue() + "%");
		case SUFFIXLIKE:
			return builder.like(expression, "%" + condition.getValue());
		case SUFFIXNOTLIKE:
			return builder.notLike(expression, "%" + condition.getValue());
		case LIKE:
			return builder.like(expression, "%" + condition.getValue() + "%");
		case INLIKE:
			return builder.like(expression, "" + condition.getValue() + "");
		case NOTLIKE:
			return builder.notLike(expression, "%" + condition.getValue() + "%");
		case ISNULL:
			return builder.isNull(expression);
		case ISNOTNULL:
			return builder.isNotNull(expression);
		case IN:
			return builder.in(expression).value(condition.getValue());
		case NOTIN:
			return builder.in(expression).value(condition.getValue()).not();
		case NULL:
			return null;
		default:
			return null;
		}
	}
	
	private static <T> List<Predicate> generatePredicates(final Collection<SearchFilter> searchFilters, Root<T> root, CriteriaBuilder builder){
		List<Predicate> predicates = Lists.newArrayList();
		for (SearchFilter searchFilter : searchFilters) {
			if (searchFilter instanceof Condition){
				Condition condition = (Condition) searchFilter;
				Predicate predicate = generatePredicate(condition, root, builder);
				if (predicate == null) continue;
				predicates.add(predicate);
			}
		}
		return predicates;
	}
}
