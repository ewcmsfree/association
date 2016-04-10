package com.ewcms.system.icon.service;

import org.springframework.stereotype.Service;

import com.ewcms.common.service.BaseService;
import com.ewcms.common.web.controller.entity.TreeIconCls;
import com.ewcms.system.icon.entity.Icon;
import com.ewcms.system.icon.repository.IconRepository;

/**
 * @author wu_zhijun
 */
@Service
public class IconService extends BaseService<Icon, Long> {

    private IconRepository getIconRepository() {
        return (IconRepository) baseRepository;
    }

    public Icon findByIdentity(String identity) {
        return getIconRepository().findByIdentity(identity);
    }
    
    @Override
    public Icon save(Icon icon){
    	icon = super.save(icon);
    	saveAndUpdateTreeIcon(null, icon);
    	return icon;
    }
    
    @Override
    public Icon update(Icon icon){
    	String dbIdentity = null;
    	if (icon != null && icon.getId() != null){
    		Icon dbIcon = findOne(icon.getId());
    		if (dbIcon != null && icon.getIdentity() != null){
    			dbIdentity = dbIcon.getIdentity();
    		}
    	}
    	
    	icon = super.update(icon);
    	saveAndUpdateTreeIcon(dbIdentity, icon);
    	return icon;
    }
    
    private void saveAndUpdateTreeIcon(String dbIdentity, Icon icon){
    	Icon treeIcon = new Icon();
    	
    	if (dbIdentity != null){
    		treeIcon = findByIdentity(dbIdentity + TreeIconCls.tree_suffix);
    	}
		treeIcon.setIdentity(icon.getIdentity() + TreeIconCls.tree_suffix);
		treeIcon.setCssClass(icon.getCssClass());
		treeIcon.setDescription(icon.getDescription());
		treeIcon.setIconType(icon.getIconType());
		treeIcon.setImgSrc(icon.getImgSrc());
		treeIcon.setLeft(icon.getLeft());
		treeIcon.setSpriteSrc(icon.getSpriteSrc());
		treeIcon.setTop(icon.getTop());
		treeIcon.setWidth(TreeIconCls.tree_width);
		treeIcon.setHeight(TreeIconCls.tree_height);
		treeIcon.setStyle("background-size:100%;");
		
		super.save(treeIcon);
    }
}
