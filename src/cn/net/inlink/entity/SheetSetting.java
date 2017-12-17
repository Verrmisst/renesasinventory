package cn.net.inlink.entity;

public class SheetSetting {
	//开始的行号
	private int startRowIndex;
	//开始的列数
	private int startcolumnIndex;
	//模板类型
	private String templet;
	
	//列数
	private int columnCount;
	
	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public String getTemplet() {
		return templet;
	}

	public void setTemplet(String templet) {
		this.templet = templet;
	}

	public SheetSetting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}

	public int getStartcolumnIndex() {
		return startcolumnIndex;
	}

	public void setStartcolumnIndex(int startcolumnIndex) {
		this.startcolumnIndex = startcolumnIndex;
	}
}
