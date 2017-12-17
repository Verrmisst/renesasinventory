package cn.net.inlink.action;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import cn.net.inlink.entity.Department;
import cn.net.inlink.service.ExportComData;
import cn.net.inlink.service.ExportWipOrFBData;
import cn.net.inlink.service.ExportXCHData;


public class ExportExcelAction  {
	// 部门名
	private String flag;


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}



	public String export() {
		
		ServletContext context = ServletActionContext.getServletContext();
		
		if (this.flag.equalsIgnoreCase("DownloadXCH")) {

			ExportXCHData xch = new ExportXCHData();

			xch.exportToXCH(context);
			
		} else if(this.flag.equalsIgnoreCase("DownloadCom")){
			
			ExportComData ecd = new ExportComData();
			
			ecd.exportToCompany(context);
			
		}else {
			ExportWipOrFBData wipfb = new ExportWipOrFBData();

			wipfb.exportToWipOrFB(this.flag,context);
		}

		return "export";
	}

}
