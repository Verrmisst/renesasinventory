package cn.net.inlink.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class ZipFileUtil {
	/**
	 * 把文件压缩成zip格式
	 * 
	 * @param files
	 *            需要压缩的文件
	 * @param zipFilePath
	 *            
	 */
	public static void compressFiles2Zip(File[]files, String zipFilePath) {

		if (files != null && files.length > 0) {

			ZipArchiveOutputStream zaos = null;

			try {
				File zipFile = new File(zipFilePath);
				zaos = new ZipArchiveOutputStream(zipFile);
				// Use Zip64 extensions for all entries where they are required
				zaos.setUseZip64(Zip64Mode.AsNeeded);

				// 将每个文件用ZipArchiveEntry封装
				// 再用ZipArchiveOutputStream写到压缩文件中
				for (File file : files) {
					if (file != null) {
						ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(
								file, file.getName());
						zaos.putArchiveEntry(zipArchiveEntry);

						InputStream is = null;

						try {
							is = new BufferedInputStream(new FileInputStream(
									file));
							byte[] buffer = new byte[1024 * 5];
							int len = -1;
							while ((len = is.read(buffer)) != -1) {
								// 把缓冲区的字节写入到ZipArchiveEntry
								zaos.write(buffer, 0, len);
							}
							// Writes all necessary data for this entry.
							zaos.closeArchiveEntry();

						} catch (Exception e) {
							throw new RuntimeException(e);

						}
					}

				}
                 
				zaos.flush();
				zaos.close();
				
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
}
