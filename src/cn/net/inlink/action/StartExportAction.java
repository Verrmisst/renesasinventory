package cn.net.inlink.action;

import java.util.List;

import cn.net.inlink.dao.QueryDaoImpl;
import cn.net.inlink.entity.Department;



public class StartExportAction {
	
	private List<Department> rlist;
	
	public List<Department> getRlist() {
		return rlist;
	}

	public void setRlist(List<Department> rlist) {
		this.rlist = rlist;
	}
	
	
	public String execute(){
		
		this.rlist = new QueryDaoImpl().queryDepartment();
		

		return "startexport";
	}
	
}
