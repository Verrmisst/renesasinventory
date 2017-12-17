package cn.net.inlink.dao;

import java.util.List;
import java.util.Map;

import cn.net.inlink.entity.CompanyCount;
import cn.net.inlink.entity.Department;
import cn.net.inlink.entity.InventoryFrameBank;
import cn.net.inlink.entity.InventoryWip;
import cn.net.inlink.entity.InventoryXCHAfter;
import cn.net.inlink.entity.InventoryXCHBefore;


/**
 * dao层：提供对数据库的操作
 *
 */
public interface QueryDao {
	    // 根据科室标记查询仕挂盘点，返回集合(部门别)
		public List<InventoryWip> queryRecod(String flag);

		// 已知科室标记 ，根据工程查询结果返回Map集合
		public Map<String, List<InventoryWip>> queryOperation(String flag);
	    
		//已知科室标记查询科室，
		public Department queryDepartmentName(String flag);
		
		// 根据科室标记查询FB盘点，返回集合(部门别)
		public List<InventoryFrameBank> queryFB(String flag);
		
		//出荷装箱前
		public List<InventoryXCHBefore> queryXCHBefore();
		
		//出荷装箱后
		public List<InventoryXCHAfter> queryXCHAfter();
		
		//查询科室的类别
		public List<Department> queryDepartment();
		
		//查询公司别汇总
		public List<CompanyCount> queryCompany();
		
		//查询各部门盘点总量(根据部门)
		public Department queryRecordQty(String flag);
		
		//查询各部门fb总数（根据部门）
		public List<InventoryFrameBank> queryFBQty(String flag);
		
		//查询按各部门个工程汇总数量(部门，工程名)
		public int query4Oper(String flag,String operation);
		
		
		
}
