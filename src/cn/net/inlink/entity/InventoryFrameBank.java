package cn.net.inlink.entity;
/**
 * 
 * 半年盘点：FB盘点实体类
 * 
 */
public class InventoryFrameBank {
	    //keyno
		private String lotname;
		
		//型名
		private String product;
		
		//生产线
		private String locationcode;
		
		//数量
		private int qty;
		
		//pkg
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

		public String getLocationcode() {
			return locationcode;
		}

		public void setLocationcode(String locationcode) {
			this.locationcode = locationcode;
		}

		public int getQty() {
			return qty;
		}

		public void setQty(int qty) {
			this.qty = qty;
		}

		public String getPkg() {
			return pkg;
		}

		public void setPkg(String pkg) {
			this.pkg = pkg;
		}

		@Override
		public String toString() {
			return "InventoryFrameBank [lotname=" + lotname + ", product=" + product + ", locationcode=" + locationcode
					+ ", qty=" + qty + ", pkg=" + pkg + "]";
		}
		
}
