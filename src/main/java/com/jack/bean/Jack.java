package com.jack.bean;

import java.util.List;

public class Jack {

	private int id;
	private String name;
	private String urlPhoto;
	private List<String> urlList;	//主页2-3个视频序列的地址
	private List<String> fileList;	//解析出后每个视频对应m3u8文件的地址
	private String fileName;
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<String> getFileList() {
		return fileList;
	}
	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}
	public Jack() {
		super();
	}
	
	@Override
	public String toString() {
		return "Jack [id=" + id + ", name=" + name + ", urlPhoto=" + urlPhoto + ", urlList=" + urlList + ", fileList="
				+ fileList + ", fileName=" + fileName + "]";
	}
	public Jack(int id, String name, String urlPhoto, List<String> urlList) {
		super();
		this.id = id;
		this.name = name;
		this.urlPhoto = urlPhoto;
		this.urlList = urlList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrlPhoto() {
		return urlPhoto;
	}
	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
	}
	public List<String> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}
	
	
}
