package cn.net.inlink.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.net.inlink.entity.CompanyCount;
import cn.net.inlink.entity.Department;
import cn.net.inlink.entity.InventoryFrameBank;
import cn.net.inlink.entity.InventoryWip;
import cn.net.inlink.entity.InventoryXCHAfter;
import cn.net.inlink.entity.InventoryXCHBefore;
import cn.net.inlink.entity.Operationname;
import cn.net.inlink.entity.WipCount;
import cn.net.inlink.utils.JdbcUtil4T;


public class QueryDaoImpl implements QueryDao{
	
	private QueryRunner qr = new QueryRunner(JdbcUtil4T.getDataSource());

	@Override
	public List<InventoryWip> queryRecod(String flag) {
		List<InventoryWip> wipList = null;

		String sql = "select operation,locationcode,department,flag,qty from WIP_STATISTICS_FINAL where flag =?";

		try {
			wipList = qr.query(sql, new BeanListHandler<InventoryWip>(InventoryWip.class), flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return wipList;
	}

	
	@Override
	public Map<String, List<InventoryWip>> queryOperation(String flag) {
		// 定义map集合 键为工程名，值为对应工程和科室下的结果集记录
		Map<String, List<InventoryWip>> operationMap = new HashMap<String, List<InventoryWip>>();

		// 定义一个集合存储查询出的作业工程（以科室作为条件）
		List<Operationname> operations = null;

		// 定义一个集合存储按工程查询出的结果集
		List<InventoryWip> operationList = null;

		String sql = "select distinct operation from WIP_STATISTICS_L where flag = ?";

		try {
			operations = qr.query(sql, new BeanListHandler<Operationname>(Operationname.class), flag);
            
			// 遍历工程集合
			for (Operationname op : operations) {
				// 以工程，部门作为条件 查询记录
				sql = "select lotname,product,prevlot,operation,locationCode,qty,pkg from WIP_STATISTICS_L where flag =? and operation = ?";
                
				operationList = qr.query(sql, new BeanListHandler<InventoryWip>(InventoryWip.class), flag,op.getOperation());
                
				// 向map集合中添加记录
				operationMap.put(op.getOperation(), operationList);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return operationMap;
	}
     
	/*@Override
	public int queryOperationRecord(String department) {
		//定义一个集合存储查询数量
		List<Integer> num = null;
		
		//定义查询数量的sql
		String sql = "select count(distinct operation) from WIP_STATISTICS_L where department=?";
		
		try {
			num = qr.query(sql, new BeanListHandler<Integer>(Integer.class), department);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return num.get(0);
	}*/
	
	
	@Override
	public List<InventoryFrameBank> queryFB(String flag) {
		// 定义List集合存储结果集
		List<InventoryFrameBank> fbList = null;

		// 定义sql，筛选条件为科室
		String sql = "select lotname,product,locationcode,qty,pkg from FB_STATISTICS_L where flag = ?";

		try {
			fbList = qr.query(sql, new BeanListHandler<InventoryFrameBank>(InventoryFrameBank.class), flag);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fbList;
	}

	@Override
	public List<InventoryXCHBefore> queryXCHBefore() {
		// 定义List集合存储查询出的结果集
		List<InventoryXCHBefore> beforeList = null;

		// 定义sql
		String sql = "select * from xch_statistics_before";

		try {
			beforeList = qr.query(sql, new BeanListHandler<InventoryXCHBefore>(InventoryXCHBefore.class));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return beforeList;
	}

	@Override
	public List<InventoryXCHAfter> queryXCHAfter() {
		// 定义List集合存储查询出的结果集
		List<InventoryXCHAfter> afterList = null;

		// 定义sql
		String sql = "select * from xch_statistics_after";

		try {
			afterList = qr.query(sql, new BeanListHandler<InventoryXCHAfter>(InventoryXCHAfter.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return afterList;
	}
	
	@Override
	public List<Department> queryDepartment() {
		
		List<Department> dlist = null;
		
		String sql = "select distinct department ,flag from PRC_WIP_DISTDEPART order by DEPARTMENT ";
		
		try {
			dlist = qr.query(sql, new BeanListHandler<Department>(Department.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dlist;
	}
	@Override
	public Department queryDepartmentName(String flag) {
		
		Department dept = null;
		
		String sql = "select department,flag from PRC_WIP_DISTDEPART where flag=?";
		
		try {
			dept = qr.query(sql, new BeanHandler<Department>(Department.class), flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return dept;
	}
	
	@Override
	public List<CompanyCount> queryCompany() {
		List<CompanyCount> comList = null;
		
		String sql = "select * from COMPANY_STATISTICS ";
		
		try {
			comList = qr.query(sql, new BeanListHandler<CompanyCount>(CompanyCount.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return comList;
	}
	
	@Override
	public Department queryRecordQty(String flag) {
		Department dept = null;
		
		String sql = "select department,flag,sum(qty) as qty from WIP_STATISTICS_FINAL where flag = ? group by department,flag ";
		
		try {
			dept = qr.query(sql, new BeanHandler<Department>(Department.class),flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dept;
	}
	
	@Override
	public List<InventoryFrameBank> queryFBQty(String flag) {
		List<InventoryFrameBank> fqList = null;
		
		String sql ="select locationcode,qty from FB_STATISTICS_FINAL where flag = ?";
		
		try {
			fqList = qr.query(sql, new BeanListHandler<InventoryFrameBank>(InventoryFrameBank.class),flag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fqList;
	}
	
	@Override
	public int query4Oper(String flag, String operation) {
		
		Department dept = null;
		
		String sql = "SELECT sum(qty) AS qty FROM wip_statistics_l WHERE flag = ? AND operation=?";
		
		try {
			dept  = qr.query(sql, new BeanHandler<Department>(Department.class),flag,operation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dept.getQty();
	}
	
	
	
}

