package cn.net.inlink.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import cn.net.inlink.entity.Rule;
import cn.net.inlink.utils.JdbcUtil;
import cn.net.inlink.utils.JdbcUtil4T;


public class RuleDaoImpl implements RuleDao {

	private QueryRunner qr = new QueryRunner(JdbcUtil4T.getDataSource());

	@Override
	public void deleteRecordByDept(String department) {
        
		
		String sql = "delete from PRC_INVENTORY_RULE where department=?";

		try {
			qr.update(sql,department);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insetInto(List<Rule> rlist) {
		String sql = "insert into PRC_INVENTORY_RULE values(?,?,?,?,?,?,?,?,?,?,?)";

		String department = rlist.get(0).getDepartment();
		
		//先进行删除操作
		deleteRecordByDept(department);
		
		for (Rule rule : rlist) {
			Object[] params = { rule.getLocationcode(), rule.getOperation(),
					rule.getPkg(), rule.getFamilycode(), rule.getProduct(),
					rule.getWait(), rule.getDepartment(), rule.getFactory(),
					rule.getResponser(), rule.getInventorier(),
					rule.getTelephone() };
			

			try {
				//插入操作
				qr.update(sql, params);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
