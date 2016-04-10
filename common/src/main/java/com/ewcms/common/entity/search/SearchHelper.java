package com.ewcms.common.entity.search;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ewcms.common.utils.EmptyUtil;
import com.google.common.collect.Lists;

/**
 *
 * @author 吴智俊
 */
public class SearchHelper {
	
	public static <T, ID> Map<String, Object> query(SearchParameter<ID> searchParameter, JpaSpecificationExecutor<T> jse, Class<T> clazz){
		Searchable searchable = parameterConverSearchable(searchParameter, clazz);
		Specification<T> spec = DynamicSpecifications.bySearchFilter(searchable, clazz);
		
		Page<T> pages = jse.findAll(spec, searchable.getPage());
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		resultMap.put("total", pages.getTotalElements());
		resultMap.put("rows", pages.getContent());
		return resultMap;
	}

	public static <T, ID> Searchable parameterConverSearchable(SearchParameter<ID> searchParameter, Class<T> clazz){
		Searchable searchable = Searchable.newSearchable();
		
		Map<String, Object> parameter = searchParameter.getParameters();
		for (Entry<String, Object> entry : parameter.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			searchable.addSearchParam(key, value);
		}
		
//		List<ID> selections = searchParameter.getSelections();
//		if (!selections.isEmpty()){
//			searchable.addSearchFilter("id", SearchOperator.IN, selections);
//		}
		
		if (!searchable.isConverted()){
			searchable.convert(clazz);
		}
		
		searchable.setPage(getPageable(searchParameter));
		
		return searchable;
	}
	
	private static <ID> List<Order> order(SearchParameter<ID> searchParameter){
		List<Order> orders = Lists.newArrayList();
		
//		Map<String, Direction> sorts = searchParameter.getSorts();
//		if (EmptyUtil.isMapNotEmpty(sorts)){
//			Iterator<Entry<String, Direction>> its = sorts.entrySet().iterator();
//			while (its.hasNext()){
//				Entry<String, Direction> entry = its.next();
//				String property = entry.getKey();
//				if (property != null && !property.isEmpty()){
//					Direction direction = entry.getValue();
//					if (direction == null) direction = Direction.ASC;
//					Order order = new Order(direction, property);
//					orders.add(order);
//				}
//			}
//		}
//		
//		String sort = searchParameter.getSort();
//		if (EmptyUtil.isStringNotEmpty(sort)){
//			Order order = null;
//			Direction direction = Direction.ASC;
//			try{
//				direction = Direction.fromString(searchParameter.getOrder());
//			}catch(IllegalArgumentException e){
//			}
//			order = new Order(direction, searchParameter.getSort());
//			orders.add(order);
//		}
//		return orders;
		
		String sort = searchParameter.getSort();
		if (EmptyUtil.isStringNotEmpty(sort)){
			Order order = null;
			Direction direction = Direction.ASC;
			try{
				direction = Direction.fromString(searchParameter.getOrder());
			}catch(IllegalArgumentException e){
			}
			order = new Order(direction, sort);
			orders.add(order);
		}else{
			Map<String, Direction> sorts = searchParameter.getSorts();
			
			if (EmptyUtil.isMapNotEmpty(sorts)){
				Iterator<Entry<String, Direction>> its = sorts.entrySet().iterator();
				while (its.hasNext()){
					Entry<String, Direction> entry = its.next();
					String property = entry.getKey();
					if (property != null && !property.isEmpty()){
						Direction direction = entry.getValue();
						if (direction == null) direction = Direction.ASC;
						Order order = new Order(direction, property);
						orders.add(order);
					}
				}
			}
		}
		return orders;
	}
	
	private static Sort sort(List<Order> orders){
		Sort sort = null;
		if (!orders.isEmpty()){
			sort = new Sort(orders);
		}
		return sort;
	}
	
	private static <ID> Pageable getPageable(SearchParameter<ID> searchParameter){
		List<Order> orders = order(searchParameter);
		Sort sort = sort(orders);
		Pageable pageable = new PageRequest(searchParameter.getPage() - 1, searchParameter.getRows(), sort);
		
		return pageable;
	}
}
