package cn.net.inlink.utils;

import java.io.File;

/**
 * 删除文件的工具类
 */
public class DeleteFileUtil {

	public static void deleteFile(String fileName) {
		
		File file = new File(fileName);
		
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
             file.delete();   
		}
	}
}
