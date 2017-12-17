package cn.net.inlink.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.net.inlink.entity.SheetSetting;
import cn.net.inlink.utils.JdbcUtil4T;

public class SheetDaoImpl implements SheetDao {
	private QueryRunner qr = new QueryRunner(JdbcUtil4T.getDataSource());

	@Override
	public SheetSetting queryByTemplet(String templet) {
		String sql = "select startRowIndex,startcolumnIndex from sheetsetting where templet = ?";

		SheetSetting setting = null;

		try {
			setting = qr.query(sql, new BeanHandler<SheetSetting>(
					SheetSetting.class), templet);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return setting;
	}

	public SheetSetting queryColumnCount() {

		String sql = "SELECT columncount FROM SHEETSETTING WHERE columncount IS NOT NULL AND ROWNUM = 1";

		SheetSetting setting = null;

		try {
			setting = qr.query(sql, new BeanHandler<SheetSetting>(
					SheetSetting.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return setting;
	}
}
