package cn.net.inlink.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.net.inlink.dao.SheetDao;
import cn.net.inlink.dao.SheetDaoImpl;
import cn.net.inlink.entity.Rule;
import cn.net.inlink.exceptions.UploadException;
import cn.net.inlink.utils.CellUtil;

/**
 * 读取上传的excel文件
 * 
 */
public class ReadUpLoadExcel {

	public List<Rule> readxls(FileInputStream fis) throws IOException,
			UploadException {

		// FileInputStream fis = new FileInputStream("D:\\1.xls");

		POIFSFileSystem pos = new POIFSFileSystem(fis);
		HSSFWorkbook wb = new HSSFWorkbook(pos);
		SheetDao sd = new SheetDaoImpl();
		Rule rule = null;
		UploadException ue = null;
		String message = null;

		List<Rule> rlist = new ArrayList<Rule>();

		HSSFSheet sheet = wb.getSheetAt(0);

		// System.out.println(sheet.getLastRowNum());

		for (int j = 0; j < sheet.getLastRowNum(); j++) {

			HSSFRow row = sheet.getRow(j + 1);

			// 该上传表格的列数
			int columnCount = row.getLastCellNum();

			System.out.println(columnCount);

			if (row != null) {
				HSSFCell str_location = row.getCell(0);
				HSSFCell str_operation = row.getCell(1);
				HSSFCell str_pkg = row.getCell(2);
				HSSFCell str_familycode = row.getCell(3);
				HSSFCell str_product = row.getCell(4);
				HSSFCell str_wait = row.getCell(5);
				HSSFCell str_department = row.getCell(6);
				HSSFCell str_factory = row.getCell(7);
				HSSFCell str_responser = row.getCell(8);
				HSSFCell str_inventorier = row.getCell(9);
				HSSFCell str_telephone = row.getCell(10);

				// 检查列数

				if (columnCount != sd.queryColumnCount().getColumnCount()) {

					message = "您上传的数据有误，请检查您的文件列数是否正确。文档列数应该为："
							+ sd.queryColumnCount().getColumnCount();

					ue = new UploadException();

					ue.setMessage(message);

					throw ue;
				}

				// 检查重要字段是否为空
				if (str_location == null || str_operation == null
						|| str_department == null
						|| str_location.toString().trim().equals("")
						|| str_operation.toString().trim().equals("")
						|| str_department.toString().trim().equals("")) {

					message = "您上传的数据有误，请检查生产线、工程、部门是否有空值。";

					ue = new UploadException();

					ue.setMessage(message);

					throw ue;
				}

				// 创建规则对象
				rule = new Rule();

				/*
				 * System.out.println(str_location + " " + str_operation + " " +
				 * str_pkg + " " + str_familycode + " " + str_product + " " +
				 * str_wait + " " + str_department + " " + str_factory + "  " +
				 * str_responser + " " + str_inventorier + " " + str_telephone);
				 */

				rule.setLocationcode(CellUtil.getStringCellValue(str_location));
				rule.setOperation(CellUtil.getStringCellValue(str_operation));
				rule.setPkg(CellUtil.getStringCellValue(str_pkg));
				rule.setFamilycode(CellUtil.getStringCellValue(str_familycode));
				rule.setProduct(CellUtil.getStringCellValue(str_product));
				rule.setWait(CellUtil.getStringCellValue(str_wait));
				rule.setDepartment(CellUtil.getStringCellValue(str_department));
				rule.setFactory(CellUtil.getStringCellValue(str_factory));
				rule.setResponser(CellUtil.getStringCellValue(str_responser));
				rule.setInventorier(CellUtil
						.getStringCellValue(str_inventorier));
				rule.setTelephone(CellUtil.getStringCellValue(str_telephone));

				rlist.add(rule);

			}
		}

		return rlist;
	}
}
