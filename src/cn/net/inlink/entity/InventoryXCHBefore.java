package cn.net.inlink.entity;
/**
 * 
 * 半年盘点：盘点出荷品装箱前实体类
 */
public class InventoryXCHBefore {
	//keyno
		private String lotname;
		
		//型名
		private String modelBody;
		
		//仓基
		private String modelRepo;
		
		//数量
		private int qty ;
		
		//状态
		private String flag;

		public String getLotname() {
			return lotname;
		}

		public void setLotname(String lotname) {
			this.lotname = lotname;
		}

		public String getModelBody() {
			return modelBody;
		}

		public void setModelBody(String modelBody) {
			this.modelBody = modelBody;
		}

		public String getModelRepo() {
			return modelRepo;
		}

		public void setModelRepo(String modelRepo) {
			this.modelRepo = modelRepo;
		}

		public int getQty() {
			return qty;
		}

		public void setQty(int qty) {
			this.qty = qty;
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

}
