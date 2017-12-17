package cn.net.inlink.dao;

import java.util.List;

import cn.net.inlink.entity.Rule;


/**
 * 
 * 对上传的文件进行操作
 * 
 */
public interface RuleDao {

	// 根据科室删除记录
	public void deleteRecordByDept(String department);
	
	//根据上传的excel进行插入操作
	public void  insetInto(List<Rule> rlist);
	  
}
