package cn.net.inlink.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * 
 * 用于判断读取excel文件单元格类型的工具类
 * 
 */
public class CellUtil {
	public static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		String value = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = "";
				}
			} else {
				value = new DecimalFormat("0").format(cell
						.getNumericCellValue());
			}
			break;
		case Cell.CELL_TYPE_FORMULA: // 导入时如果为公式生成的数据则无值
			// System.out.println("Formula:" + cell.getStringCellValue());
			if (!cell.getStringCellValue().equals("")) {
				value = cell.getStringCellValue();
			} else {
				value = cell.getNumericCellValue() + "";
			}
			break;
		case Cell.CELL_TYPE_BLANK:
			break;
		case Cell.CELL_TYPE_ERROR:
			value = "";
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			value = (cell.getBooleanCellValue() == true ? "Y" : "N");
			break;
		default:
			value = "";
		}
		return value;
	}
}
