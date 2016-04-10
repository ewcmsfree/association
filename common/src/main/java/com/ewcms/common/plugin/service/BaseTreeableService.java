package com.ewcms.common.plugin.service;

import com.ewcms.common.entity.BaseSequenceEntity;
import com.ewcms.common.entity.search.SearchOperator;
import com.ewcms.common.entity.search.Searchable;
import com.ewcms.common.entity.search.filter.SearchFilter;
import com.ewcms.common.entity.search.filter.SearchFilterHelper;
//import com.ewcms.common.plugin.entity.TreeGridExtendable;
import com.ewcms.common.plugin.entity.Treeable;
import com.ewcms.common.repository.RepositoryHelper;
import com.ewcms.common.service.BaseService;
import com.ewcms.common.utils.EmptyUtil;
import com.ewcms.common.utils.ReflectUtils;
import com.ewcms.common.web.controller.entity.TreeNode;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.aop.framework.AopContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wu_zhijun
 */
public abstract class BaseTreeableService<M extends BaseSequenceEntity<ID> & Treeable<ID>, ID extends Serializable> extends BaseService<M, ID> {

	private final String DELETE_ROOT_CHILDREN_QL;
    private final String DELETE_CHILDREN_QL;
    private final String UPDATE_CHILDREN_PARENT_IDS_QL;
    private final String FIND_SELF_AND_NEXT_SIBLINGS_QL;
    private final String FIND_NEXT_WEIGHT_QL;

    private RepositoryHelper repositoryHelper;

    //权重的步长
    private final Integer stepLength;
    
    /**
     * 权重的步长 默认1000
     *
     * @return
     */
    public Integer getStepLength() {
        return stepLength;
    }
    
    protected BaseTreeableService() {
    	this(1000);
    }
    
    protected BaseTreeableService(Integer stepLength){
    	this.stepLength = stepLength;
        Class<M> entityClass = ReflectUtils.findParameterizedType(getClass(), 0);
        repositoryHelper = new RepositoryHelper(entityClass);
        String entityName = repositoryHelper.getEntityName(entityClass);

        DELETE_ROOT_CHILDREN_QL = String.format("delete from %s where parentId=?1 or parentIds like concat(?2, %s)", entityName, "'%'");
        DELETE_CHILDREN_QL = String.format("delete from %s where id=?1 or parentIds like concat(?2, %s)", entityName, "'%'");
        UPDATE_CHILDREN_PARENT_IDS_QL = String.format("update %s set parentIds=(?1 || substring(parentIds, length(?2)+1)) where parentIds like concat(?2, %s)", entityName, "'%'");
        FIND_SELF_AND_NEXT_SIBLINGS_QL = String.format("from %s where parentIds =?1 and weight>=?2 order by weight asc", entityName);
        FIND_NEXT_WEIGHT_QL = String.format("select case when max(weight) is null then 1 else (max(weight) + 1) end from %s where parentId=?1", entityName);
    }

    @Override
    public M save(M m) {
        if (m.getWeight() == null) {
            m.setWeight(nextWeight(m.getParentId()));
        }
        return super.save(m);
    }
    
    @Transactional
    public void deleteSelfAndChild(M m) {
    	String defaultQL = DELETE_CHILDREN_QL;
    	if (m.isRoot()) {
    		defaultQL = DELETE_ROOT_CHILDREN_QL;
    	}
    	repositoryHelper.batchUpdate(defaultQL, m.getId(), m.makeSelfAsNewParentIds());
    }

    public void deleteSelfAndChild(List<M> mList) {
        for (M m : mList) {
            deleteSelfAndChild(m);
        }
    }

    public M appendChild(M parent, M child){
    	child.setParentId(parent.getId());
    	child.setParentIds(parent.makeSelfAsNewParentIds());
    	child.setWeight(nextWeight(parent.getId()));
    	save(child);
    	
    	return child;
    }

    public int nextWeight(ID id) {
        return repositoryHelper.<Integer>findOne(FIND_NEXT_WEIGHT_QL, id);
    }


    /**
     * 移动节点
     * 根节点不能移动
     *
     * @param source   源节点
     * @param target   目标节点
     * @param moveType 位置(append:插入,top:之前,bottom:之后)
     */
    public void move(M source, M target, String moveType) {
        if (source == null || target == null || source.isRoot()) { //根节点不能移动
            return;
        }

        //如果是相邻的兄弟 直接交换weight即可
        boolean isSibling = source.getParentId().equals(target.getParentId());
        boolean isNextOrPrevMoveType = "bottom".equals(moveType) || "top".equals(moveType);
        if (isSibling && isNextOrPrevMoveType && Math.abs(source.getWeight() - target.getWeight()) == 1) {
            //无需移动
            if ("bottom".equals(moveType) && source.getWeight() > target.getWeight()) return;
            if ("top".equals(moveType) && source.getWeight() < target.getWeight()) return;

            int sourceWeight = source.getWeight();
            source.setWeight(target.getWeight());
            target.setWeight(sourceWeight);
            update(source);
            update(target);
            return;
        }

        //移动到目标节点之后
        if ("bottom".equals(moveType)) {
            List<M> siblings = findSelfAndNextSiblings(target.getParentIds(), target.getWeight());
            siblings.remove(0);//把自己移除

            if (siblings.size() == 0) { //如果没有兄弟了 则直接把源的设置为目标即可
                int nextWeight = nextWeight(target.getParentId());
                updateSelftAndChild(source, target.getParentId(), target.getParentIds(), nextWeight);
                return;
            } else {
                moveType = "top";
                target = siblings.get(0); //否则，相当于插入到实际目标节点下一个节点之前
            }
        }

        //移动到目标节点之前
        if ("top".equals(moveType)) {

            List<M> siblings = findSelfAndNextSiblings(target.getParentIds(), target.getWeight());
            //兄弟节点中包含源节点
            if (siblings.contains(source)) {
                // 1 2 [3 source] 4
                siblings = siblings.subList(0, siblings.indexOf(source) + 1);
                int firstWeight = siblings.get(0).getWeight();
                for (int i = 0; i < siblings.size() - 1; i++) {
                    siblings.get(i).setWeight(siblings.get(i + 1).getWeight());
                }
                siblings.get(siblings.size() - 1).setWeight(firstWeight);
            } else {
                // 1 2 3 4  [5 new]
                int nextWeight = nextWeight(target.getParentId());
                int firstWeight = siblings.get(0).getWeight();
                for (int i = 0; i < siblings.size() - 1; i++) {
                    siblings.get(i).setWeight(siblings.get(i + 1).getWeight());
                }
                siblings.get(siblings.size() - 1).setWeight(nextWeight);
                source.setWeight(firstWeight);
                updateSelftAndChild(source, target.getParentId(), target.getParentIds(), source.getWeight());
            }

            return;
        }
        //否则作为最后孩子节点
        int nextWeight = nextWeight(target.getId());
        updateSelftAndChild(source, target.getId(), target.makeSelfAsNewParentIds(), nextWeight);
    }


    /**
     * 把源节点全部变更为目标节点
     *
     * @param source
     * @param newParentIds
     */
    private void updateSelftAndChild(M source, ID newParentId, String newParentIds, int newWeight) {
        String oldSourceChildrenParentIds = source.makeSelfAsNewParentIds();
        
        source.setParentId(newParentId);
        source.setParentIds(newParentIds);
        source.setWeight(newWeight);
        
        String newSourceChildrenParentIds = source.makeSelfAsNewParentIds();
        repositoryHelper.batchUpdate(UPDATE_CHILDREN_PARENT_IDS_QL, newSourceChildrenParentIds, oldSourceChildrenParentIds);
        
        update(source);
    }

    /**
     * 查找目标节点及之后的兄弟  注意：值与越小 越排在前边
     *
     * @param parentIds
     * @param currentWeight
     * @return
     */
    protected List<M> findSelfAndNextSiblings(String parentIds, int currentWeight) {
        return repositoryHelper.<M>findAll(FIND_SELF_AND_NEXT_SIBLINGS_QL, parentIds, currentWeight);
    }


    /**
     * 查看与name模糊匹配的名称
     *
     * @param name
     * @return
     */
    public Set<String> findNames(Searchable searchable, String name, ID excludeId) {
        M excludeM = findOne(excludeId);

        searchable.addSearchFilter("name", SearchOperator.LIKE, name);
        addExcludeSearchFilter(searchable, excludeM);

        return Sets.newHashSet(
                Lists.transform(
                        findAll(searchable).getContent(),
                        new Function<M, String>() {
                            @Override
                            public String apply(M input) {
                                return input.getName();
                            }
                        }
                )
        );
    }

    /**
     * 查询子子孙孙
     *
     * @return
     */
    public List<M> findChildren(List<M> parents, Searchable searchable) {

        if (parents.isEmpty()) {
            return Lists.newArrayList();
        }

        SearchFilter first = SearchFilterHelper.newCondition("parentIds", SearchOperator.PREFIXLIKE, parents.get(0).makeSelfAsNewParentIds());
        SearchFilter[] others = new SearchFilter[parents.size() - 1];
        for (int i = 1; i < parents.size(); i++) {
            others[i - 1] = SearchFilterHelper.newCondition("parentIds", SearchOperator.PREFIXLIKE, parents.get(i).makeSelfAsNewParentIds());
        }
        searchable.or(first, others);

        List<M> children = findAllWithSort(searchable);
        return children;
    }

    public List<M> findAllByName(Searchable searchable, M excludeM) {
        addExcludeSearchFilter(searchable, excludeM);
        return findAllWithSort(searchable);
    }

    /**
     * 查找根和一级节点
     *
     * @param searchable
     * @return
     */
    public List<M> findRootAndChild(Searchable searchable) {
        searchable.addSearchParam("EQ_parentId", 0);
        List<M> models = findAllWithSort(searchable);

        if (models.size() == 0) {
            return models;
        }
        List<ID> ids = Lists.newArrayList();
        for (int i = 0; i < models.size(); i++) {
            ids.add(models.get(i).getId());
        }
        searchable.removeSearchFilter("EQ_parentId");
        searchable.addSearchParam("IN_parentId", ids);

        models.addAll(findAllWithSort(searchable));

        return models;
    }

    public Set<ID> findAncestorIds(Iterable<ID> currentIds) {
        Set<ID> parents = Sets.newHashSet();
        for (ID currentId : currentIds) {
            parents.addAll(findAncestorIds(currentId));
        }
        return parents;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<ID> findAncestorIds(ID currentId) {
        Set ids = Sets.newHashSet();
        M m = findOne(currentId);
        if (m == null) {
            return ids;
        }
        for (String idStr : StringUtils.tokenizeToStringArray(m.getParentIds(), "/")) {
            if (!StringUtils.isEmpty(idStr)) {
                ids.add(Long.valueOf(idStr));
            }
        }
        return ids;
    }

    /**
     * 递归查询祖先
     *
     * @param parentIds
     * @return
     */
    public List<M> findAncestor(String parentIds) {
        if (StringUtils.isEmpty(parentIds)) {
            return Lists.newArrayList();
        }
        String[] ids = StringUtils.tokenizeToStringArray(parentIds, "/");
        List<Long> idList = Lists.newArrayList();
        for (int i = 0; i < ids.length; i++){
        	idList.add(Long.parseLong(ids[i]));
        }
        
        Searchable searchable = Searchable.newSearchable();
        
        searchable.addSearchFilter("id", SearchOperator.IN, idList);
        
    	Sort.Order parentIdsAsc = new Sort.Order(Sort.Direction.DESC, "parentIds");
        Sort.Order weightAsc = new Sort.Order(Sort.Direction.DESC, "weight");
        Sort sort = new Sort(parentIdsAsc, weightAsc);
        searchable.addSort(sort);
       
        return Lists.reverse(findAllWithSort(searchable));
    }
    
    /**
     * 查询树型文件全路径
     * 
     * @param currentName 当前名称
     * @param parentIds 父节点ID路径
     * @param separator 分隔符
     * @return 全路径
     */
    public String findNames(String currentName, String parentIds, Boolean showRootName, String separator){
    	StringBuilder s = new StringBuilder();
		
		List<M> parents = findAncestor(parentIds);
		for (M o : parents){
			if (!showRootName && o.isRoot()) continue;
			//if (o.isRoot())	continue;
			s.append(o.getName() + "" + separator + "");
		}
		s.append(currentName);
		return s.toString();
    }
    
    public void addExcludeSearchFilter(Searchable searchable, M excludeM) {
        if (excludeM == null) {
            return;
        }
        searchable.addSearchFilter("id", SearchOperator.NE, excludeM.getId());
        searchable.addSearchFilter("parentIds", SearchOperator.SUFFIXNOTLIKE, excludeM.makeSelfAsNewParentIds());
    }

    @SuppressWarnings("unchecked")
	public void reweight() {
        int batchSize = 100;
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Pageable pageable = new PageRequest(0, batchSize, sort);
        Page<M> page = findAll(pageable);
        do {
            //doReweight需要requiresNew事务
            ((BaseTreeableService<M, ID>) AopContext.currentProxy()).doReweight(page);

            RepositoryHelper.clear();

            if (page.isLast()) {
                break;
            }

            pageable = new PageRequest((pageable.getPageNumber() + 1) * batchSize, batchSize, sort);

            page = findAll(pageable);

        } while (true);
    }

    public void doReweight(Page<M> page) {
        int totalElements = (int) page.getTotalElements();
        List<M> moves = page.getContent();

        int firstElement = page.getNumber() * page.getSize();

        for (int i = 0; i < moves.size(); i++) {
            M move = moves.get(i);
            move.setWeight((totalElements - firstElement - i) * stepLength);
            update(move);
        }

    }
    
    /**
     * 只显示根节点和一级节点树，其他节点通过页面点击子节点再临时调用
     * 
     * @param parentId 
     * @return
     */
    public List<TreeNode<ID>> tree(ID parentId, Searchable searchable, Boolean expandAll){
    	searchable = treeSort(searchable);
    	
    	List<TreeNode<ID>> treeNodes = Lists.newArrayList();
    	
    	if (parentId == null){
    		searchable.addSearchFilter("parentId", SearchOperator.EQ, 0);
        	List<M> roots = findAllWithSort(searchable);
        	if (EmptyUtil.isCollectionEmpty(roots)) {
        		M m = initRoot();
        		roots.add(m);
        	}

        	for (M root : roots){
        		TreeNode<ID> rootNode = convertToTreeNode(root, null, expandAll);
        		searchable.removeSearchFilter("parentId", SearchOperator.EQ);
        		searchable.addSearchFilter("parentId", SearchOperator.EQ, root.getId());
        		List<M> childrens = findAllWithSort(searchable);
        		if (EmptyUtil.isCollectionNotEmpty(childrens)) {
        			List<TreeNode<ID>> treeChildren = Lists.newArrayList();
        			for (M children : childrens){
        				treeChildren.add(convertToTreeNode(children, null, expandAll));
        			}
        			rootNode.setChildren(treeChildren);
        		}
        		treeNodes.add(rootNode);
        	}
    	} else {
    		searchable.addSearchFilter("parentId", SearchOperator.EQ, parentId);
    		List<M> childrens = findAllWithSort(searchable);
    		if (EmptyUtil.isCollectionNotEmpty(childrens)) {
    			for (M children : childrens){
    				TreeNode<ID> treeNode = convertToTreeNode(children, null, expandAll);
    				treeNodes.add(treeNode);
    			}
    		}
    	}
    	return treeNodes;
    }
    
    /**
     * 显示所有节点，并通过 checkId 定位到选中的节点(单选)
     * 
     * @param parentId
     * @param checkId
     * @return
     */
    public List<TreeNode<ID>> treeSingleChecked(ID parentId, ID checkId, Searchable searchable){
    	searchable = treeSort(searchable);
        M check = null;
        if (checkId != null){
        	check = findOne(checkId);
        }
        List<M> checks = Lists.newArrayList();
        if (check != null) checks.add(check);
        
    	List<TreeNode<ID>> treeNodes = Lists.newArrayList();
    	getTreeNode(treeNodes, searchable, parentId, checks, false);
    	
    	return treeNodes;
    }
    
    /**
     * 显示所有节点，并通过 checkIds 定位到选中的节点(多选)
     * 
     * @param parentId
     * @param checkIds
     * @return
     */
    public List<TreeNode<ID>> treeMultipleChecked(ID parentId, ID[] checkIds, Searchable searchable){
    	searchable = treeSort(searchable);
    	
    	List<M> checks = Lists.newArrayList();
    	for (ID checkId : checkIds){
    		M check = findOne(checkId);
    		if (check == null) continue;
    		checks.add(check);
    	}
    	
    	List<TreeNode<ID>> treeNodes = Lists.newArrayList();
    	getTreeNode(treeNodes, searchable, parentId, checks, false);
    	
    	return treeNodes;
    }

    public List<TreeNode<ID>> table(Searchable searchable){
    	searchable = treeSort(searchable);
    	
    	List<TreeNode<ID>> treeNodes = Lists.newArrayList();
    	getTreeNode(treeNodes, searchable, null, null, true);
    	
    	return treeNodes;    
    }
    
    public TreeNode<ID> covertToTreeNode(M m){
    	Map<String, Object> attributes = treeAttributes(m);
    	if (EmptyUtil.isMapEmpty(attributes)){
    		attributes = Maps.newHashMap();
    	}
        
        attributes.put("root", m.isRoot());
        attributes.put("isParent", m.isHasChildren());
        attributes.put("pId", m.getParentId());
        
    	TreeNode<ID> treeNode = new TreeNode<ID>();
    	
        treeNode.setAttributes(attributes);
        treeNode.setId(m.getId());
        treeNode.setText(m.getName());
        treeNode.setIconCls(m.getIcon());
        
        treeNode.setState("open");
        
        return treeNode;
    }
    
    private Searchable treeSort(Searchable searchable){
    	if (EmptyUtil.isNull(searchable)){
    		searchable = Searchable.newSearchable();
    	}
    	Sort.Order parentIdsAsc = new Sort.Order(Sort.Direction.ASC, "parentIds");
        Sort.Order weightAsc = new Sort.Order(Sort.Direction.ASC, "weight");
        Sort sort = new Sort(parentIdsAsc, weightAsc);
        searchable.addSort(sort);
        
        return searchable;
    }
    
    /**
     * 树型对象转换成页面可显示的对象
     * 
     * @param m 树型对象
     * @param check 被选中的树型对象
     * @param expandAll 是否展开
     * @return
     */
    private TreeNode<ID> convertToTreeNode(M m, List<M> checks, Boolean expandAll) {
    	Map<String, Object> attributes = treeAttributes(m);
    	if (EmptyUtil.isMapEmpty(attributes)){
    		attributes = Maps.newHashMap();
    	}
        
        attributes.put("root", m.isRoot());
        attributes.put("isParent", m.isHasChildren());
        attributes.put("pId", m.getParentId());
        
    	TreeNode<ID> treeNode = new TreeNode<ID>();
    	
        treeNode.setAttributes(attributes);
        treeNode.setId(m.getId());
        treeNode.setText(m.getName());
        treeNode.setIconCls(m.getIcon());
        
        if (checks != null && !checks.isEmpty()){
        	for (M check : checks){
        		if (check.getParentIds().indexOf(m.getId().toString()) > 0){
        			treeNode.setState("open");
        		} else {
        			treeNode.setState("closed");
        		}
        		if (check.getId().equals(m.getId())){
        			treeNode.setChecked(true);
        		}
        	}
    	} else {
    		if (!m.isRoot()) {
	    		if (m.isHasChildren()) {
	            	treeNode.setState("closed");
	            }else{
	            	treeNode.setState("open");
	            }
    		}
    	}
        
        if (expandAll) {
        	treeNode.setState("open");
        } else {
        	if (m.isLeaf()) treeNode.setState("open");
        }
        
        return treeNode;
    }
    
    private void getTreeNode(List<TreeNode<ID>> treeNodes, Searchable searchable, ID parentId, List<M> checks, Boolean expandAll){
    	if (EmptyUtil.isNull(parentId)) {
    		searchable.addSearchFilter("parentId", SearchOperator.EQ, 0L);
    	} else {
    		searchable.addSearchFilter("parentId", SearchOperator.EQ, parentId);
    	}
    	List<M> parents = findAllWithSort(searchable);
    	if (parents.isEmpty()) {
    		if (parentId == null){
    			M m = initRoot();
    			parents.add(m);
    		} else {
    			return;
    		}
    	}

    	for (M parent : parents){
    		TreeNode<ID> parentNode = convertToTreeNode(parent, checks, expandAll);
    		searchable.removeSearchFilter("parentId", SearchOperator.EQ);
    		if (parent.isHasChildren()){
    			List<TreeNode<ID>> childrenNodes = Lists.newArrayList();
    			getTreeNode(childrenNodes, searchable, parent.getId(), checks, expandAll);
    			parentNode.setChildren(childrenNodes);
    		}
    		treeNodes.add(parentNode);
    	}
    }
    
    /**
     * 初始化根节点
     * 
     * @return M
     */
    @Transactional
    public abstract M initRoot();
    
    /**
     * 树型扩展数据
     * 
     * @param m
     * @return Map
     */
    public abstract Map<String, Object> treeAttributes(M m);
}
