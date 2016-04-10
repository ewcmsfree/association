package com.ewcms.system.icon.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.common.entity.BaseSequenceEntity;

/**
 * 图标
 * 
 * <ul>
 * <li>identity:前台使用的名称</li>
 * <li>cssClass:样式类名字</li>
 * <li>imgSrc:图片地址</li>
 * <li>spriteSrc:css背景图位置</li>
 * <li>left:与spriteSrc左边距离</li>
 * <li>top:与spriteSrc上边距离</li>
 * <li>width:宽度</li>
 * <li>height:高度</li>
 * <li>style:额外css样式</li>
 * <li>iconType:图标类型</li>
 * <li>description:描述</li>
 * </ul>
 *
 * @author 吴智俊
 */
@Entity
@Table(name = "sys_icon")
@SequenceGenerator(name="seq", sequenceName="seq_sys_icon_id", allocationSize = 1)
public class Icon extends BaseSequenceEntity<Long> {

	private static final long serialVersionUID = 7509627698622053347L;

	@Column(name = "identity", unique = true)
	private String identity;
	@Column(name = "css_class")
	private String cssClass;
	@Column(name = "img_src")
	private String imgSrc;
	@Column(name = "sprite_src")
	private String spriteSrc;
	@Column(name = "icon_left")
	private Integer left;
	@Column(name = "icon_top")
	private Integer top;
	@Column(name = "icon_width")
	private Integer width;
	@Column(name = "icon_height")
	private Integer height;
	@Column(name = "icon_style")
	private String style = "";
	@Column(name = "icon_type")
	@Enumerated(EnumType.STRING)
	private IconType iconType;
	@Column(name = "description")
	private String description;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getSpriteSrc() {
		return spriteSrc;
	}

	public void setSpriteSrc(String spriteSrc) {
		this.spriteSrc = spriteSrc;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public IconType getIconType() {
		return iconType;
	}

	public void setIconType(IconType iconType) {
		this.iconType = iconType;
	}
	
	public String getIconTypeInfo(){
		return iconType == null ? "" : iconType.getInfo();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
