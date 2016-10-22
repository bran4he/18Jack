package com.jack.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class CommonDownload implements Runnable {

	private static Logger logger = Logger.getLogger(CommonDownload.class);
	
	private String fileName;
	private String fileUrl;
	private String savePath;	//eg:   /home/bran/xueqiu
	
	private boolean timeFlag = false;	//eg:	2016-03-12-filename
	
	private int bufferSize = 10;		//默认10*1024字节

	private String saveToFile;		//当fileUrl == null, 保存此String到文件
	
	
	public String getSaveToFile() {
		return saveToFile;
	}

	public void setSaveToFile(String saveToFile) {
		this.saveToFile = saveToFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public boolean isTimeFlag() {
		return timeFlag;
	}

	public void setTimeFlag(boolean timeFlag) {
		this.timeFlag = timeFlag;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	
	
	public CommonDownload(String fileName, String fileUrl, String savePath, boolean timeFlag, int bufferSize) {
		super();
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.savePath = savePath;
		this.timeFlag = timeFlag;
		this.bufferSize = bufferSize;
	}

	public CommonDownload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 保存文件名，文件url，保存路径
	 * 默认时间戳false，默认buffersize 10kb
	 * @param fileName
	 * @param photoUrl
	 * @param savePath
	 */
	public CommonDownload(String fileName, String fileUrl, String savePath) {
		super();
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.savePath = savePath;
	}

	@Override
	public void run() {
		logger.info("start download file ：" + fileName);
		long start = System.currentTimeMillis();
		
		File fold = new File(savePath);
		if(!fold.exists()){
			fold.mkdirs();
		}
		
		//保存String到文件
		if(fileUrl == null){
			PrintWriter pw;
			try {
				pw = new PrintWriter(
							new File(fold +File.separator+fileName));
				pw.write(saveToFile);
				pw.close();
			} catch (FileNotFoundException e) {
				logger.error("file is not exist:" +fileName);
				e.printStackTrace();
			} 
			
			
			return;	//直接返回
		}
		
		File file;
		
		//需要加时间戳区分下载文件
		if(timeFlag){
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYY-MM-dd");
			String prefix = sdf.format(date);
			
			file = new File(fold +File.separator+prefix+"-"+fileName);
		}else{
			file = new File(fold +File.separator+fileName);
		}
		
		URL url;
		FileOutputStream fos = null;
		InputStream ins = null;
		try {
			url = new URL(fileUrl);
			URLConnection conn = url.openConnection();
			conn.setConnectTimeout(5000);
			
			if(file.exists() && file.length() == conn.getContentLength()){
				logger.info("file is exist ："+ file.getCanonicalPath());
			}else{
				ins = conn.getInputStream();
				fos = new FileOutputStream(file);
				byte[] buffer = new byte[bufferSize * 1024];
				int len =0;
				while((len = ins.read(buffer)) != -1){
					fos.write(buffer, 0, len);
				}
				
				long end = System.currentTimeMillis();
				logger.info("end to download file：" + fileName +"，takes :"+(end-start)/1000 +"s");
			}
			
		} catch (MalformedURLException e) {
			logger.error("url error　："+fileUrl);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("downloading file error："+ file.getName());
			e.printStackTrace();
		}finally {
			if(fos != null){
				try {
					fos.close();
				} catch (Exception e2) {
					logger.error("close fos failed");
					e2.printStackTrace();
				}
			}
			if(ins != null){
				try {
					ins.close();
				} catch (Exception e3) {
					logger.error("close ins failed");
					e3.printStackTrace();
				}
			}
		}
	}

}
