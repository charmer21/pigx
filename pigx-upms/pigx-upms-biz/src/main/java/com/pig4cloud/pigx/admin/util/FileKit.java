package com.pig4cloud.pigx.admin.util;

import java.io.File;
import java.math.BigDecimal;

/**
 * @author: Luckly
 * @date: 2019-06-18
 * @description: 文件套件
 */
public class FileKit {

	private final static String[] TYPE_IMAGE = new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"};
	private final static String[] TYPE_VIDEO = new String[]{".flv", ".swf", ".mkv", ".avi", ".rm", ".rmvb", ".mpeg", ".mpg", ".ogv", ".mov", ".wmv", ".mp4", ".webm"};
	private final static String[] TYPE_AUDIO = new String[]{".mp3", ".wav", ".mid", ".ogg"};
	private final static String[] TYPE_DOC = new String[]{".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf"};


	/**
	 * 判断文件大小
	 *
	 * @param len  文件长度
	 * @param size 限制大小
	 * @param unit 限制单位（B,K,M,G）
	 * @return
	 */
	public static boolean checkFileSize(Long len, int size, String unit) {
		double fileSize = 0;
		if ("B".equals(unit.toUpperCase())) {
			fileSize = (double) len;
		} else if ("K".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1024;
		} else if ("M".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1048576;
		} else if ("G".equals(unit.toUpperCase())) {
			fileSize = (double) len / 1073741824;
		} else {
			fileSize = (double) len;
		}
		if (fileSize > size) {
			return false;
		}
		return true;
	}

	/**
	 * 转换文件大小为友好显示
	 *
	 * @param size
	 * @return
	 */
	public static String getDisplaySize(long size) {
		String displaySize = size + "";
		if (size < 1024) {
			displaySize = size + "B";
		} else if (size < 1024 * 1024) {
			displaySize = getDisplaySizeKB(size);
		} else if (size < 1024 * 1024 * 1024) {
			displaySize = getDisplaySizeMB(size);
		} else if (size < 1024.0 * 1024 * 1024 * 1024) {
			displaySize = getDisplaySizeGB(size);
		} else if (size < 1024.0 * 1024 * 1024 * 1024 * 1024) {
			displaySize = getDisplaySizeTB(size);
		}
		return displaySize;
	}

	/**
	 * 转换文件大小为友好显示
	 *
	 * @param size
	 * @return
	 */
	public static String getDisplaySizeKB(long size) {
		return new BigDecimal(size).divide(new BigDecimal(1024)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "KB";
	}

	/**
	 * 转换文件大小为友好显示
	 *
	 * @param size
	 * @return
	 */
	public static String getDisplaySizeMB(long size) {
		return new BigDecimal(size).divide(new BigDecimal(1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "MB";
	}

	/**
	 * 转换文件大小为友好显示
	 *
	 * @param size
	 * @return
	 */
	public static String getDisplaySizeGB(long size) {
		return new BigDecimal(size).divide(new BigDecimal(1024 * 1024 * 1024.0)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "GB";
	}

	/**
	 * 转换文件大小为友好显示
	 *
	 * @param size
	 * @return
	 */
	public static String getDisplaySizeTB(long size) {
		return new BigDecimal(size).divide(new BigDecimal(1024.0 * 1024 * 1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue() + "TB";
	}


	/**
	 * 获取文件类型
	 *
	 * @param path
	 * @return
	 */
	public static String getType(String path) {
		String type = "file";
		String suffix = getSuffix(path);
		if (eqSuffix(suffix, TYPE_IMAGE))
			type = "image";
		else if (eqSuffix(suffix, TYPE_VIDEO))
			type = "video";
		else if (eqSuffix(suffix, TYPE_AUDIO))
			type = "audio";
		else if (eqSuffix(suffix, TYPE_DOC))
			type = "doc";
		return type;
	}

	/**
	 * 获取文件后缀名
	 *
	 * @param path 文件绝对路径
	 * @return
	 */
	public static String getSuffix(String path) {
		int idx = path.lastIndexOf(".");
		if (idx < 0)
			return "";
		return path.substring(path.lastIndexOf(".")).toLowerCase();
	}

	private static boolean eqSuffix(String suffix, String[] types) {
		for (String type : types) {
			if (type.equals(suffix))
				return true;
		}
		return false;
	}

	/**
	 * 获取文件后缀名
	 *
	 * @param file 文件
	 * @return
	 */
	public static String getSuffix(File file) {
		return getSuffix(file.getName());
	}

}
