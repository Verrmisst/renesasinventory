package cn.net.inlink.entity;
/**
 * 
 * 半年盘点：盘点出荷品装箱后实体类
 */
public class InventoryXCHAfter {
	// 箱号
		private String pkgID;

		// 捆包数量
		private int baleQty;

		// 箱数量
		private int boxQty;

		// 订单号
		private String orderNo;

		// 分纳号
		private int branchNo;

		// 状态
		private String flag;

		public String getPkgID() {
			return pkgID;
		}

		public void setPkgID(String pkgID) {
			this.pkgID = pkgID;
		}

		public int getBaleQty() {
			return baleQty;
		}

		public void setBaleQty(int baleQty) {
			this.baleQty = baleQty;
		}

		public int getBoxQty() {
			return boxQty;
		}

		public void setBoxQty(int boxQty) {
			this.boxQty = boxQty;
		}

		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		public int getBranchNo() {
			return branchNo;
		}

		public void setBranchNo(int branchNo) {
			this.branchNo = branchNo;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}
}
