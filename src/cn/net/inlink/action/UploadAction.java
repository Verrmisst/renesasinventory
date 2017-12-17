package cn.net.inlink.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import cn.net.inlink.dao.RuleDaoImpl;
import cn.net.inlink.entity.Rule;
import cn.net.inlink.exceptions.UploadException;
import cn.net.inlink.service.ReadUpLoadExcel;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	private String bussiness;
	private String savePath;// 配置文件

	private String errorMessage;

	private String message;

	/* 针对上传的临时文件 */
	private File upload;
	private String uploadContentType;
	private String uploadFileName;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	/* 确定输出流的在web服务器硬盘上的具体位置 */
	public String getSavePath() {
		/*
		 * (1)ServletActionContext是ActionContext的子类，目的是 在Action中提供jsp的内置对象
		 * (2)利用ServletContext对象获得指定长传路径的真实路径
		 */
		ServletContext application = ServletActionContext.getServletContext();
		String path = application.getRealPath(savePath);
		return path;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String execute() throws Exception {

		System.out.println(this.bussiness);

		if (this.bussiness.equals("1")) {

			System.out.println("开始上传文件------------------");
			System.out.println("savePath=" + getSavePath());
			System.out.println("uploadFileName=" + getUploadFileName());
			System.out.println("uploadContentType=" + getUploadContentType());
			System.out.println("upload=" + this.getUpload());

			// 目标文件（服务器存储位置）
			FileOutputStream fos = new FileOutputStream(getSavePath()
					+ File.separator + getUploadFileName());
			// 上传文件的输入流
			FileInputStream fis = new FileInputStream(getUpload());

			try {
				ReadUpLoadExcel rde = new ReadUpLoadExcel();

				List<Rule> rlist = rde.readxls(fis);

				RuleDaoImpl rdi = new RuleDaoImpl();

				// 进行插入操作
				rdi.insetInto(rlist);
			} catch (UploadException e) {

				errorMessage = e.getMessage();
				
				
				System.out.println(errorMessage);

				return "errorMessage";

			}

			// 创建缓冲区

			/*byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer);
			}*/

			return SUCCESS;
		}

		message = "请选择正确的业务！";

		return ERROR;
	}

}
