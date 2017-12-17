package cn.net.inlink.test;

import java.io.File;

import cn.net.inlink.utils.ZipFileUtil;


public class ZipTest {
	public static void main(String[] args) {
		
		File file1 = new File("D:\\EdwardConversation\\Workspaces\\MyEclipse 10\\RenesasHalfInventory\\WebRoot\\excelfile\\制造二科(FB).xls");
		
		File file2 = new File("D:\\EdwardConversation\\Workspaces\\MyEclipse 10\\RenesasHalfInventory\\WebRoot\\excelfile\\制造二科(仕挂).xls");
		
		ZipFileUtil.compressFiles2Zip(new File[]{file1,file2}, "D:\\aa.zip");
		
	}
}
