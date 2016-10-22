package com.jack.common.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 用于过滤文件夹中suffix结尾的文件
 * @author bran
 *
 */
public class SuffixFileFilter implements FilenameFilter {

	private String fileSuffix;
	
	public SuffixFileFilter(String fileSuffix) {
		super();
		this.fileSuffix = fileSuffix;
	}

	public SuffixFileFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}


	@Override
	public boolean accept(File dir, String name) {
		if(name.toLowerCase().endsWith(fileSuffix)){
			return true;
		}else{
			return false;
		}
	}

}
