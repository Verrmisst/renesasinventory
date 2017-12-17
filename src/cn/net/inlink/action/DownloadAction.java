package cn.net.inlink.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import cn.net.inlink.dao.QueryDaoImpl;
import cn.net.inlink.entity.Department;


public class DownloadAction {

	// 部门名称标记
	private String flag;
  
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) throws UnsupportedEncodingException {
		this.flag = new String(flag.getBytes("iso-8859-1"),"utf-8");
	}

	public InputStream getInputStream() throws UnsupportedEncodingException,
			FileNotFoundException {

		/*HttpServletResponse response = ServletActionContext.getResponse();
		// attachment,以附件的方式下载文件,会打开保存文件对话框;inline,以内联的方式下载,浏览器会直接打开文件
		response.setHeader("Content-Disposition", "attachment;fileName="
				+department);*/// java.net.URLEncoder.encode(fileName,"UTF-8")
																	// 编码转换，解决乱码

		// 如果fileName是相对路径
		
		QueryDaoImpl qdi = new QueryDaoImpl();
		
		Department dept = qdi.queryDepartmentName(flag);
		
		String department = dept.getDepartment();
		
		System.out.println(ServletActionContext.getServletContext().getRealPath("/"+"excelzips/"+department+".zip"));
		
		//return ServletActionContext.getServletContext().getResourceAsStream(fileName);
		return new FileInputStream(ServletActionContext.getServletContext().getRealPath("/"+"excelzips/"+department+".zip"));
		// 如果fileName是绝对路径
		
		 //return new BufferedInputStream(new FileInputStream(fileName));
	}

	public String execute() {
		
     
        
		return "download";
	}

}
