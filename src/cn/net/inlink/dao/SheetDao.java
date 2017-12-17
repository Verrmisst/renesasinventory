package cn.net.inlink.dao;

import cn.net.inlink.entity.SheetSetting;

/**
 * 
 * 获取初始坐标
 */
public interface SheetDao {
	//根据对应模板查询初始坐标
	public SheetSetting queryByTemplet(String templet);
	
	//查询列数
	public SheetSetting queryColumnCount();
}
