package com.ewcms.personnel.archive.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.SearchParameter;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.web.controller.BaseController;
import com.ewcms.personnel.archive.entity.ArchiveHistory;
import com.ewcms.personnel.archive.service.ArchiveHistoryService;

@Controller
@RequestMapping(value = "/personnel/archive/history")
public class ArchiveHistoryController extends BaseController<ArchiveHistory, Long>{

	@Autowired
	private ArchiveHistoryService archiveHistoryService;
	
	@RequestMapping(value = "{archiveId}/index")
	public String index(@PathVariable(value = "archiveId")Long archiveId){
		return viewName("index");
	}
	
	@RequestMapping(value = "{archiveId}/query")
	@ResponseBody
	public Map<String, Object> query(@ModelAttribute SearchParameter<Long> searchParameter, @PathVariable(value = "archiveId")Long archiveId){
		searchParameter.getParameters().put("EQ_archiveId", archiveId);
		searchParameter.getSorts().put("opDate", Direction.DESC);
		
		return archiveHistoryService.query(searchParameter);
	}

	@RequestMapping(value = "last")
	@ResponseBody
	public ArchiveHistory last(@RequestParam(value = "archiveId", required = false) Long archiveId) {
		if (EmptyUtil.isNull(archiveId)) return null;

		Searchable searchable = Searchable.newSearchable();
		
		searchable.addSearchFilter("archiveId", SearchOperator.EQ, archiveId);
		searchable.addSort(Direction.DESC, "opDate");
		searchable.setPage(0, 1);

		Page<ArchiveHistory> pages = archiveHistoryService.findAll(searchable);
		List<ArchiveHistory> histories = pages.getContent();
		
		if (EmptyUtil.isCollectionEmpty(histories)) return null;
		return histories.get(0);
	}
}
