package com.xiaowu.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseProduct<M extends BaseProduct<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setPrice(java.lang.String price) {
		set("price", price);
	}

	public java.lang.String getPrice() {
		return get("price");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}

	public java.lang.Integer getType() {
		return get("type");
	}

	public void setStick(java.lang.Integer stick) {
		set("stick", stick);
	}

	public java.lang.Integer getStick() {
		return get("stick");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public void setIsDel(java.lang.Integer isDel) {
		set("is_del", isDel);
	}

	public java.lang.Integer getIsDel() {
		return get("is_del");
	}

}