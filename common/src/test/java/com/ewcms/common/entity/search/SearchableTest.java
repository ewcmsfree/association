package com.ewcms.common.entity.search;

import java.util.ArrayList;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.support.DefaultFormattingConversionService;

import com.ewcms.common.entity.User;
import com.ewcms.common.entity.UserStatus;
import com.ewcms.common.entity.search.exception.InvlidSearchOperatorException;
import com.ewcms.common.entity.search.filter.SearchFilterHelper;
import com.ewcms.common.entity.search.utils.SearchableConvertUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author wu_zhijun
 *
 */
public class SearchableTest {
	
	private ConversionService oldConversionService;
	
	@Before
	public void setUp(){
		try{
			oldConversionService = SearchableConvertUtils.getConversionService();
		} catch (Exception e){
		}
		SearchableConvertUtils.setConversionService(new DefaultFormattingConversionService());
	}
	
	@After
	public void tearDown(){
		SearchableConvertUtils.setConversionService(oldConversionService);
	}
	
	@Test
	public void testNewSearchable(){
		Map<String, Object> searchParam1 = Maps.newHashMap();
		searchParam1.put("LIKE_name", "1234");
		searchParam1.put("LIKE_name", "5678");
		searchParam1.put("EQ_age", 1);
		Searchable searchable1 = Searchable.newSearchable(searchParam1);
		
		Assert.assertTrue(searchable1.containsSearchKey("LIKE_name"));
		Assert.assertTrue(searchable1.containsSearchKey("EQ_age"));
		Assert.assertEquals("5678", searchable1.getValue("LIKE_name"));
		Assert.assertEquals(1, searchable1.getValue("EQ_age"));
		Assert.assertEquals(2, searchable1.getSearchFilters().size());
		
		Searchable searchable2 = Searchable.newSearchable(null, new PageRequest(0, 1), new Sort(Sort.Direction.ASC, "uuid"));
		
		Assert.assertTrue(searchable2.hasPageable());
		Assert.assertTrue(searchable2.hashSort());
		
		Searchable searchable3 = Searchable.newSearchable(null, new PageRequest(0, 1, new Sort(Sort.Direction.ASC, "uuid")));
		
		Assert.assertTrue(searchable3.hasPageable());
		Assert.assertTrue(searchable3.hashSort());
	}
	
	@Test(expected = InvlidSearchOperatorException.class)
	public void testNewSearchableWithErrorOperator(){
		Map<String, Object> searchParam1 = Maps.newHashMap();
		searchParam1.put("ABCD_name", "1234");
		
		Searchable.newSearchable(searchParam1);
	}
	
	@Test
	public void testAddParam(){
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParam("abc", "1234");
		searchable.addSearchParam("LIKE_hig", "89");
		
		Assert.assertEquals(1, searchable.getSearchFilters().size());
		
		Assert.assertFalse(searchable.containsSearchKey("abc"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_abc"));
		Assert.assertTrue(searchable.containsSearchKey("LIKE_hig"));
	}
	
	@Test
	public void testAssAllParam(){
		Map<String, Object> searchParam1 = Maps.newHashMap();
		searchParam1.put("LIKE_name", "123");
		searchParam1.put("LIKE_name", "456");
		searchParam1.put("EQ_age", 10);
		
		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchParams(searchParam1);
		searchable.addSearchParam("LIKE_address", "789");
		
		Assert.assertEquals(3, searchable.getSearchFilters().size());
		
		Assert.assertTrue(searchable.containsSearchKey("LIKE_name"));
		Assert.assertTrue(searchable.containsSearchKey("EQ_age"));
		Assert.assertTrue(searchable.containsSearchKey("LIKE_address"));
	}
	
	@Test
	public void testAddSearchFilter(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.addSearchFilter("name", SearchOperator.LIKE, "123");
		searchable.addSearchFilter("xyz", SearchOperator.CUSTOM, "456");
		
		searchable.addSearchFilter(SearchFilterHelper.newCondition("LIKE_address", "789"));
		searchable.addSearchFilter(SearchFilterHelper.newCondition("like_age", "123"));
		searchable.addSearchFilter(SearchFilterHelper.newCondition("CUSTOM_abc", "234"));
		searchable.addSearchFilter(SearchFilterHelper.newCondition("def", "567"));
		
		searchable.addSearchFilter(SearchFilterHelper.newCondition("hig", SearchOperator.EQ, "345"));
		searchable.addSearchFilter(SearchFilterHelper.newCondition("xxx", SearchOperator.CUSTOM, "567"));
		
		Assert.assertTrue(searchable.containsSearchKey("LIKE_name"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_xyz"));
		
		Assert.assertTrue(searchable.containsSearchKey("LIKE_address"));
		Assert.assertFalse(searchable.containsSearchKey("like_age"));
		Assert.assertTrue(searchable.containsSearchKey("LIKE_age"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_abc"));
		Assert.assertFalse(searchable.containsSearchKey("def"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_def"));
		
		Assert.assertTrue(searchable.containsSearchKey("EQ_hig"));
		Assert.assertFalse(searchable.containsSearchKey("xxx"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_xxx"));
	}
	
	@Test
	public void testAddSearchFilters(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.addSearchFilters(Lists.newArrayList(
				SearchFilterHelper.newCondition("like_sex", "female"), 
				SearchFilterHelper.newCondition("custom_name", "xyz"), 
				SearchFilterHelper.newCondition("realname", "321")
				)
		);
		
		Assert.assertTrue(searchable.containsSearchKey("LIKE_sex"));
		Assert.assertFalse(searchable.containsSearchKey("name"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_name"));
		Assert.assertFalse(searchable.containsSearchKey("realname"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_realname"));
	}
	
	@Test
	public void testAndSearchFilters(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.and(
				SearchFilterHelper.newCondition("LIKE_sex", "female"),
				SearchFilterHelper.newCondition("CUSTOM_name", "xyz"),
				SearchFilterHelper.newCondition("realname", "123")
		);
		
		Assert.assertEquals(1, searchable.getSearchFilters().size());
		
		Assert.assertTrue(searchable.containsSearchKey("LIKE_sex"));
		Assert.assertFalse(searchable.containsSearchKey("realname"));
	}
	
	@Test
	public void testOrSearchFilters(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.or(
				SearchFilterHelper.newCondition("LIKE_sex", "female"),
				SearchFilterHelper.newCondition("CUSTOM_name", "xyz"),
				SearchFilterHelper.newCondition("realname", "123")
		);
		
		Assert.assertEquals(1, searchable.getSearchFilters().size());
		
		Assert.assertTrue(searchable.containsSearchKey("LIKE_sex"));
		Assert.assertFalse(searchable.containsSearchKey("CUSTOM_name"));
		Assert.assertFalse(searchable.containsSearchKey("realname"));
	}
	
	@Test
	public void testRemoveSearchFilter(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.addSearchParam("a", "123");
		searchable.addSearchParam("b", "123");
		searchable.addSearchParam("c", "123");
		
		searchable.or(
				SearchFilterHelper.newCondition("LIKE_sex", "female"),
				SearchFilterHelper.newCondition("CUSTOM_name", "xyz"),
				SearchFilterHelper.newCondition("realname", "123"),
				SearchFilterHelper.newCondition("CUSTOM_birthday", "2016"),
				SearchFilterHelper.newCondition("EQ_age", 36)
		);
		
		searchable.removeSearchFilter("a");
		searchable.removeSearchFilter("CUSTOM_b");
		searchable.removeSearchFilter("LIKE_sex");
		searchable.removeSearchFilter("CUSTOM_realname");
		searchable.removeSearchFilter("birthday");
		
		Assert.assertEquals(1, searchable.getSearchFilters().size());
	}
	
	@Test
	public void testPageAndSort(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.setPage(0, 10);
		
		searchable.addSort(Sort.Direction.ASC, "uuid");
		searchable.addSort(Sort.Direction.DESC, "name");
		
		Assert.assertEquals(0, searchable.getPage().getPageNumber());
		Assert.assertEquals(10, searchable.getPage().getPageSize());
		
		Assert.assertEquals("name", searchable.getPage().getSort().iterator().next().getProperty());
		Assert.assertEquals("name", searchable.getSort().iterator().next().getProperty());
	}
	
	@Test
	public void testConvert(){
		Searchable searchable = Searchable.newSearchable();
		
		searchable.addSearchParam("LIKE_username", "wuzhijun");
		searchable.addSearchParam("EQ_status", "normal");
		searchable.addSearchParam("IN_id", new String[]{"1", "2", "3"});
		
		searchable.convert(User.class);
		
		Assert.assertTrue(searchable.isConverted());
		
		Assert.assertEquals(UserStatus.normal, searchable.getValue("EQ_status"));
		
		Assert.assertEquals(3, ((ArrayList)searchable.getValue("IN_id")).size());
		
		Assert.assertTrue(((ArrayList)searchable.getValue("IN_id")).contains(1L));
	}
}
