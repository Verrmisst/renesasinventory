package cn.net.inlink.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.net.inlink.dao.QueryDaoImpl;
import cn.net.inlink.dao.SheetDaoImpl;
import cn.net.inlink.entity.Department;
import cn.net.inlink.entity.InventoryFrameBank;
import cn.net.inlink.entity.InventoryWip;
import cn.net.inlink.entity.SheetSetting;
import cn.net.inlink.utils.DeleteFileUtil;
import cn.net.inlink.utils.ZipFileUtil;

/**
 * 
 * 业务层： 提供将数据库中的值导入到固定模板中的方法(wip\fb)
 * 
 */
public class ExportWipOrFBData {

	private QueryDaoImpl qd = new QueryDaoImpl();
	private SheetDaoImpl sdi = new SheetDaoImpl();

	// 参数：科室。盘点仕挂、FB
	public void exportToWipOrFB(String flag, ServletContext context) {

		File file1 = null;
		File file2 = null;

		InputStream fis1 = null;
		InputStream fis2 = null;
		FileOutputStream fos1 = null;
		FileOutputStream fos2 = null;

		// 获取工程根目录
		String path = context.getRealPath("/");

		Department dept = qd.queryDepartmentName(flag);

		// 获取文件名称
		String department = dept.getDepartment();

		// 先删除已有文件
		DeleteFileUtil.deleteFile(path + "/excel/" + department + "(仕挂).xls");
		DeleteFileUtil.deleteFile(path + "/excel/" + department + "(FB).xls");
		DeleteFileUtil.deleteFile(path + "/excelzips/" + department + ".zip");

		try {

			// 处理中文乱码问题
			// department = new String(department.getBytes("iso-8859-1"),
			// "utf-8");

			// 读取excel模板
			// fis1 = new
			// FileInputStream("/RenesasHalfInventory/excel/wip.xls");

			fis1 = ExportWipOrFBData.class.getClassLoader()
					.getResourceAsStream("wip.xls");

			// System.out.println(fis1);

			POIFSFileSystem pos1 = new POIFSFileSystem(fis1);

			// 新建工作簿
			HSSFWorkbook workbook1 = new HSSFWorkbook(pos1);

			// wip盘点

			// 获取第一张表
			HSSFSheet sheetwip1 = workbook1.getSheetAt(0);
			// 获取第二张表
			HSSFSheet sheetwip2 = workbook1.getSheetAt(1);

			// 获取表头
			HSSFRow rowwip1 = sheetwip1.getRow(0);

			// 设置表格边框
			// PaintBorderUtil.setBorder(rowwip1);

			// 封装目的地
			fos1 = new FileOutputStream(path + "/excel/" + department
					+ "(仕挂).xls");

			// 对第一张表进行操作

			// 获取起始行号,列号
			SheetSetting setting1 = sdi.queryByTemplet("wip");

			int rowIndex1 = setting1.getStartRowIndex();

			int columnIndex1 = setting1.getStartcolumnIndex();

			// 起始行号后追加数据
			List<InventoryWip> wipList = qd.queryRecod(flag);

			for (int i = 0; i < wipList.size() + 1; i++) {
				rowwip1 = sheetwip1.getRow((short) rowIndex1 + i);

				if (i == wipList.size()) {

					rowwip1.getCell(columnIndex1).setCellValue("合计");
					rowwip1.getCell(columnIndex1 + 3).setCellValue(
							qd.queryRecordQty(flag).getQty());
				} else {
					// 编号
					rowwip1.getCell(columnIndex1).setCellValue(i + 1);
					// 工程名
					rowwip1.getCell(columnIndex1 + 1).setCellValue(
							wipList.get(i).getOperation());
					// 仕挂工厂
					rowwip1.getCell(columnIndex1 + 2).setCellValue(
							wipList.get(i).getLocationCode());
					// 数量
					rowwip1.getCell(columnIndex1 + 3).setCellValue(
							wipList.get(i).getQty());
				}

			}

			workbook1.setSheetName(0, "仕挂盘点清单 (" + department + ")");

			// 隐藏模板sheet
			workbook1.setSheetHidden(1, true);

			// workbook1.cloneSheet(1);
			// workbook1.setSheetName(2, "111");

			// 定义一个计数变量
			int count = 1;

			// 获取工程名，及其对应的记录集
			Map<String, List<InventoryWip>> operationMap = qd
					.queryOperation(flag);

			Set<String> operations = operationMap.keySet();
			
			//定义一个计数变量
			int sum = 0;
			
			for (String oper : operations) {
				// 计数变量自增
				count++;
				// 复制表
				HSSFSheet sheetx = workbook1.cloneSheet(1);

				// 重名名表
				workbook1.setSheetName(count, oper);

				// 获取表头
				HSSFRow rowx = sheetx.getRow(0);

				// 获取工程名对应的结果集
				List<InventoryWip> operRecords = operationMap.get(oper);

				// 获取各部门各工程的汇总数量
				int qtyAll = qd.query4Oper(flag, oper);

				//获取集合的大小
				int size = operRecords.size();
				
				sum += size;
				
				//System.out.println(size);
				
				//System.out.println(sum);

				for (int i = 0; i < operRecords.size() + 1; i++) {

					rowx = sheetx.getRow((short) rowIndex1 + i);

					if (i == operRecords.size()) {
						rowx.getCell(columnIndex1).setCellValue("合计");
						rowx.getCell(columnIndex1 + 6).setCellValue(qtyAll);

					} else {

						rowx.getCell(columnIndex1).setCellValue(i + 1+sum-size);
						rowx.getCell(columnIndex1 + 1).setCellValue(
								operRecords.get(i).getLotname());
						rowx.getCell(columnIndex1 + 2).setCellValue(
								operRecords.get(i).getProduct());
						rowx.getCell(columnIndex1 + 3).setCellValue(
								operRecords.get(i).getPrevlot());
						rowx.getCell(columnIndex1 + 4).setCellValue(
								operRecords.get(i).getLocationCode());
						rowx.getCell(columnIndex1 + 5).setCellValue(oper);
						rowx.getCell(columnIndex1 + 6).setCellValue(
								operRecords.get(i).getQty());
						rowx.getCell(columnIndex1 + 7).setCellValue(
								operRecords.get(i).getPkg());
					}

				}

			}

			fos1.flush();
			workbook1.write(fos1);

			// ////////////////////////////////////////////////////////////////////////////////////////////////////

			// 封装wip文件路径
			file1 = new File(path + "/excel/" + department + "(仕挂).xls");

			// FB盘点

			// FB记录集
			List<InventoryFrameBank> fbList = qd.queryFB(flag);

			// FB总qty记录集
			List<InventoryFrameBank> fqList = qd.queryFBQty(flag);

			// 有记录则写入excel文件
			if (fbList.size() != 0 && fqList.size() != 0) {

				// 读取excel模板
				// fis2 = new FileInputStream("/fb.xls");
				fis2 = ExportWipOrFBData.class.getClassLoader()
						.getResourceAsStream("fb.xls");

				POIFSFileSystem pos2 = new POIFSFileSystem(fis2);

				// 新建工作簿
				HSSFWorkbook workbook2 = new HSSFWorkbook(pos2);

				// 重命名sheet
				workbook2.setSheetName(0, "FB盘点清单(" + department + ")");
				workbook2.setSheetName(1, "A26");

				// 获取第一张表
				HSSFSheet sheetfb1 = workbook2.getSheetAt(0);
				// 获取第二张表
				HSSFSheet sheetfb2 = workbook2.getSheetAt(1);

				// 获取第一张表的表头
				HSSFRow rowfb1 = sheetfb1.getRow(0);
				// 获取第二张表的表头
				HSSFRow rowfb2 = sheetfb2.getRow(0);

				// 绘制表格边框
				// PaintBorderUtil.setBorder(rowfb1);
				// PaintBorderUtil.setBorder(rowfb2);

				// 封装目的地
				fos2 = new FileOutputStream(path + "/excel/" + department
						+ "(FB).xls");

				// 获取起始行号,列号
				SheetSetting setting2 = sdi.queryByTemplet("fb");

				int rowIndex2 = setting2.getStartRowIndex();

				int columnIndex2 = setting2.getStartcolumnIndex();

				for (int a = 0; a < fqList.size(); a++) {
					rowfb1 = sheetfb1.getRow((short) rowIndex2 + a);

					// 第一张表

					if (a == fqList.size() - 1) {
						rowfb1.getCell(columnIndex2).setCellValue("合计");
						rowfb1.getCell(columnIndex2 + 3).setCellValue(
								fqList.get(a).getQty());
					} else {
						rowfb1.getCell(columnIndex2).setCellValue(a + 1);
						rowfb1.getCell(columnIndex2 + 1).setCellValue("A26");
						rowfb1.getCell(columnIndex2 + 2).setCellValue(
								fqList.get(a).getLocationcode());
						rowfb1.getCell(columnIndex2 + 3).setCellValue(
								fqList.get(a).getQty());
					}
				}

				for (int i = 0; i < fbList.size(); i++) {
					rowfb2 = sheetfb2.getRow((short) rowIndex2 + i);
					// 第二张表
					rowfb2.getCell(columnIndex2).setCellValue(i + 1);
					rowfb2.getCell(columnIndex2 + 1).setCellValue(
							fbList.get(i).getLotname());
					rowfb2.getCell(columnIndex2 + 2).setCellValue(
							fbList.get(i).getProduct());
					rowfb2.getCell(columnIndex2 + 3).setCellValue(
							fbList.get(i).getLocationcode());
					rowfb2.getCell(columnIndex2 + 4).setCellValue("A26");
					rowfb2.getCell(columnIndex2 + 5).setCellValue(
							fbList.get(i).getQty());
					rowfb2.getCell(columnIndex2 + 6).setCellValue(
							fbList.get(i).getPkg());

				}

				fos2.flush();
				workbook2.write(fos2);

				fos2.close();
				fis2.close();

				// 部门别fb存在盘点现象，将文件压缩成zip文件

				file2 = new File(path + "/excel/" + department + "(FB).xls");

				ZipFileUtil.compressFiles2Zip(new File[] { file1, file2 }, path
						+ "/excelzips/" + department + ".zip");

			} else {
				// 部门只盘点仕挂的情况，压缩仕挂文件
				ZipFileUtil.compressFiles2Zip(new File[] { file1 }, path
						+ "/excelzips/" + department + ".zip");
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos1.close();

				fis1.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
