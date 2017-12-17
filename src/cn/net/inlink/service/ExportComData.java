package cn.net.inlink.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.net.inlink.dao.QueryDaoImpl;
import cn.net.inlink.dao.SheetDaoImpl;
import cn.net.inlink.entity.CompanyCount;
import cn.net.inlink.entity.SheetSetting;
import cn.net.inlink.utils.DeleteFileUtil;
import cn.net.inlink.utils.ZipFileUtil;



public class ExportComData {
	
	private QueryDaoImpl qd = new QueryDaoImpl();
	private SheetDaoImpl sdi = new SheetDaoImpl();

	// 盘点公司别各部门总数量

	public void exportToCompany(ServletContext context) {
		InputStream fis = null;
		FileOutputStream fos = null;

		String path = context.getRealPath("/");

		// 先删除文件
		DeleteFileUtil.deleteFile(path + "/excel/" + "公司别.xls");
		DeleteFileUtil.deleteFile(path + "/excelzips/" + "公司别.zip");

		try {

			fis = ExportComData.class.getClassLoader().getResourceAsStream(
					"company.xls");

			POIFSFileSystem pos = new POIFSFileSystem(fis);

			// 新建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook(pos);

			// 获取第一张表
			HSSFSheet sheet = workbook.getSheetAt(0);

			// 重命名
			workbook.setSheetName(0, "盘点清单（汇总）");

			// 获取第一张表表头
			HSSFRow row = sheet.getRow(0);

			// 封装目的地
			fos = new FileOutputStream(path + "/excel/" + "公司别.xls");

			// 获取起始行号,列号
			SheetSetting ss = sdi.queryByTemplet("company");

			int rowIndex = ss.getStartRowIndex();

			int columnIndex = ss.getStartcolumnIndex();

			// 公司别汇总结果集
			List<CompanyCount> comList = qd.queryCompany();

			// 写入表中
			for (int i = 0; i < comList.size(); i++) {

				row = sheet.getRow((short) rowIndex + i);
				
				if (i == comList.size() - 1) {
					row.getCell(columnIndex).setCellValue(comList.get(i).getDepartment());
					row.getCell(columnIndex+2).setCellValue(comList.get(i).getQty());
					
				} else {

					row.getCell(columnIndex).setCellValue(i + 1);
					row.getCell(columnIndex + 1).setCellValue(
							comList.get(i).getDepartment());
					row.getCell(columnIndex + 2).setCellValue(
							comList.get(i).getQty());
				}
			}

			fos.flush();
			workbook.write(fos);

			File file = new File(path + "/excel/" + "公司别.xls");

			// 压缩文件
			ZipFileUtil.compressFiles2Zip(new File[] { file }, path
					+ "/excelzips/" + "公司别.zip");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
