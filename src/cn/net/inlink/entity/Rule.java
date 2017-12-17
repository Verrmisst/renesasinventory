package cn.net.inlink.entity;

/**
 * 
 * 上传的文档字段对应实体类
 * 
 */
public class Rule {
	// 生产线
	private String locationcode;
	// 工程名
	private String operation;
	// pkg
	private String pkg;
	// 大品种
	private String familycode;
	// 型名
	private String product;
	// 待定
	private String wait;
	// 部门
	private String department;
	// 工厂
	private String factory;
	// 反馈者
	private String responser;
	// 盘点担当
	private String inventorier;
	// 电话
	private String telephone;

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getFamilycode() {
		return familycode;
	}

	public void setFamilycode(String familycode) {
		this.familycode = familycode;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getWait() {
		return wait;
	}

	public void setWait(String wait) {
		this.wait = wait;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getResponser() {
		return responser;
	}

	public void setResponser(String responser) {
		this.responser = responser;
	}

	public String getInventorier() {
		return inventorier;
	}

	public void setInventorier(String inventorier) {
		this.inventorier = inventorier;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

}
