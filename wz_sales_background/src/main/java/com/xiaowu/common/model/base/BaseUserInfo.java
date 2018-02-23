package com.xiaowu.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserInfo<M extends BaseUserInfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setUserNo(java.lang.String userNo) {
		set("user_no", userNo);
	}

	public java.lang.String getUserNo() {
		return get("user_no");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}

	public java.lang.String getPhone() {
		return get("phone");
	}

	public void setTeamArea(java.lang.String teamArea) {
		set("team_area", teamArea);
	}

	public java.lang.String getTeamArea() {
		return get("team_area");
	}

	public void setTeamName(java.lang.String teamName) {
		set("team_name", teamName);
	}

	public java.lang.String getTeamName() {
		return get("team_name");
	}

	public void setOtherInfo(java.lang.String otherInfo) {
		set("other_info", otherInfo);
	}

	public java.lang.String getOtherInfo() {
		return get("other_info");
	}

	public void setPosition(java.lang.String position) {
		set("position", position);
	}

	public java.lang.String getPosition() {
		return get("position");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}

	public java.lang.String getSex() {
		return get("sex");
	}

	public void setJoinDate(java.util.Date joinDate) {
		set("join_date", joinDate);
	}

	public java.util.Date getJoinDate() {
		return get("join_date");
	}

	public void setChangeDate(java.util.Date changeDate) {
		set("change_date", changeDate);
	}

	public java.util.Date getChangeDate() {
		return get("change_date");
	}

	public void setWeixinNickname(java.lang.String weixinNickname) {
		set("weixin_nickname", weixinNickname);
	}

	public java.lang.String getWeixinNickname() {
		return get("weixin_nickname");
	}

	public void setState(java.lang.Integer state) {
		set("state", state);
	}

	public java.lang.Integer getState() {
		return get("state");
	}

	public void setCertificationTime(java.lang.String certificationTime) {
		set("certification_time", certificationTime);
	}

	public java.lang.String getCertificationTime() {
		return get("certification_time");
	}

	public void setNickName(java.lang.String nickName) {
		set("nick_name", nickName);
	}

	public java.lang.String getNickName() {
		return get("nick_name");
	}

	public void setOpenid(java.lang.String openid) {
		set("openid", openid);
	}

	public java.lang.String getOpenid() {
		return get("openid");
	}

	public void setPlatform(java.lang.String platform) {
		set("platform", platform);
	}

	public java.lang.String getPlatform() {
		return get("platform");
	}

	public void setModel(java.lang.String model) {
		set("model", model);
	}

	public java.lang.String getModel() {
		return get("model");
	}

	public void setSystem(java.lang.String system) {
		set("system", system);
	}

	public java.lang.String getSystem() {
		return get("system");
	}

	public void setVersion(java.lang.String version) {
		set("version", version);
	}

	public java.lang.String getVersion() {
		return get("version");
	}

}