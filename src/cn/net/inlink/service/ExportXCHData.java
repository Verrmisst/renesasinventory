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
import cn.net.inlink.entity.InventoryXCHAfter;
import cn.net.inlink.entity.InventoryXCHBefore;
import cn.net.inlink.entity.SheetSetting;
import cn.net.inlink.utils.DeleteFileUtil;
import cn.net.inlink.utils.ZipFileUtil;

public class ExportXCHData {

	private QueryDaoImpl qd = new QueryDaoImpl();
	private SheetDaoImpl sdi = new SheetDaoImpl();

	// 盘点出荷(装箱前、装箱后)
	public void exportToXCH(ServletContext context) {

		InputStream fis = null;
		FileOutputStream fos = null;

		String path = context.getRealPath("/");

		// 先删除文件
		DeleteFileUtil.deleteFile(path + "/excel/" + "出荷盘点表.xls");
		DeleteFileUtil.deleteFile(path + "/excelzips/" + "出荷厂.zip");

		try {
			// fis = new FileInputStream("excel\\xch.xls");

			fis = ExportXCHData.class.getClassLoader().getResourceAsStream(
					"xch.xls");

			POIFSFileSystem pos = new POIFSFileSystem(fis);

			// 新建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook(pos);

			// 获取第一张表
			HSSFSheet sheet1 = workbook.getSheetAt(0);
			// 获取第二张表
			HSSFSheet sheet2 = workbook.getSheetAt(1);

			// 获取第一张表表头
			HSSFRow row1 = sheet1.getRow(0);
			// 获取第二张表表头
			HSSFRow row2 = sheet2.getRow(0);

			// 绘制表格边框
			// PaintBorderUtil.setBorder(row1);
			// PaintBorderUtil.setBorder(row2);

			// 封装目的地
			fos = new FileOutputStream(path + "/excel/" + "出荷盘点表.xls");

			// 获取起始行号,列号
			SheetSetting ss = sdi.queryByTemplet("xch");

			int rowIndex = ss.getStartRowIndex();

			int columnIndex = ss.getStartcolumnIndex();

			// 装箱前记录集
			List<InventoryXCHBefore> beforeList = qd.queryXCHBefore();

			// 装箱后记录集
			List<InventoryXCHAfter> afterList = qd.queryXCHAfter();

			// 写入第一张表
			for (int i = 0; i < beforeList.size(); i++) {

				row1 = sheet1.getRow((short) rowIndex + i);

				if (i == beforeList.size() - 1) {
					row1.getCell(columnIndex).setCellValue(
							beforeList.get(i).getLotname());
					row1.getCell(columnIndex + 4).setCellValue(
							beforeList.get(i).getQty());

				} else {

					row1.getCell(columnIndex).setCellValue(i + 1);
					row1.getCell(columnIndex + 1).setCellValue(
							beforeList.get(i).getLotname());
					row1.getCell(columnIndex + 2).setCellValue(
							beforeList.get(i).getModelBody());
					row1.getCell(columnIndex + 3).setCellValue(
							beforeList.get(i).getModelRepo());
					row1.getCell(columnIndex + 4).setCellValue(
							beforeList.get(i).getQty());
					row1.getCell(columnIndex + 5).setCellValue(
							beforeList.get(i).getFlag());
				}

			}

			// 写入第二张表
			for (int i = 0; i < afterList.size(); i++) {
				row2 = sheet2.getRow((short) rowIndex + i);
				
				if (i == afterList.size() - 1) {
					row2.getCell(columnIndex).setCellValue(
							afterList.get(i).getPkgID());
					row2.getCell(columnIndex + 2).setCellValue(
							afterList.get(i).getBaleQty());
					row2.getCell(columnIndex + 3).setCellValue(
							afterList.get(i).getBoxQty());

				} else {

				
				row2.getCell(columnIndex).setCellValue(i + 1);
				row2.getCell(columnIndex + 1).setCellValue(
						afterList.get(i).getPkgID());
				row2.getCell(columnIndex + 2).setCellValue(
						afterList.get(i).getBaleQty());
				row2.getCell(columnIndex + 3).setCellValue(
						afterList.get(i).getBoxQty());
				row2.getCell(columnIndex + 4).setCellValue(
						afterList.get(i).getOrderNo());
				row2.getCell(columnIndex + 5).setCellValue(
						afterList.get(i).getBranchNo());
				row2.getCell(columnIndex + 6).setCellValue(
						afterList.get(i).getFlag());
				}

			}

			fos.flush();
			workbook.write(fos);

			File file = new File(path + "/excel/" + "出荷盘点表.xls");

			// 压缩文件
			ZipFileUtil.compressFiles2Zip(new File[] { file }, path
					+ "/excelzips/" + "出荷厂.zip");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
