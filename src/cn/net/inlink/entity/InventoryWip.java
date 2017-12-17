package cn.net.inlink.entity;

/**
 * 半年盘点：盘点仕挂实体类
 * 
 */
public class InventoryWip {
	// keyno
	private String lotname;

	// 型名
	private String product;

	// wafer
	private String prevlot;

	// 工程名
	private String operation;

	// 生产线
	private String locationCode;

	// 数量
	private int qty;

	// pkg
	private String pkg;

	public String getLotname() {
		return lotname;
	}

	public void setLotname(String lotname) {
		this.lotname = lotname;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPrevlot() {
		return prevlot;
	}

	public void setPrevlot(String prevlot) {
		this.prevlot = prevlot;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public InventoryWip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
}
