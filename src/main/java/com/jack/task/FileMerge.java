package com.jack.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.jack.common.util.JackConstant;
import com.jack.common.util.SuffixFileFilter;

public class FileMerge extends TimerTask{
	
	private static Logger logger = Logger.getLogger(FileMerge.class);
	
	public static void main(String[] args) {
		FileMerge f = new FileMerge();
		f.run();
	}
	
	public void merge() {
//		String savePath =System.getProperty("user.home") +"/jack/jack";
	    
		File root = new File(JackConstant.SAVE_PATH);
		File[] folders = root.listFiles();
		
		if(folders != null){
			
			int count =0;
			
			for(File folder : folders){
				
				//System.out.println(folder.getAbsolutePath());
				//System.out.println(folder.getName());
				
				count ++;
				//System.out.println(folder.getName());
				//遍历一个视频目录
				if(folder.isDirectory()){
					String path = folder.getAbsolutePath()+File.separator;
					File fileMP4 = new File(path+folder.getName()+".mp4");
					
					//如果已经存在MP4文件，则退出这个循环
					if(fileMP4.exists()){
						logger.info(folder.getName()+"存在MP4文件，取消合并");
						continue; 	//退出此次循环，进行下一个folder
					}
					
					String[] tsFiles = folder.list(new SuffixFileFilter("ts")); 
					
					//folder里没有一个readme文件
					if(tsFiles == null || (tsFiles != null && tsFiles.length == 0)){
						logger.info(folder.getName()+"没有.ts文件，取消合并");
						continue; 	//退出此次循环，进行下一个folder
					}
					
					List<String> tsFilesList = Arrays.asList(tsFiles);
					
					//自定义排序功能，实现ts文件排序
					Collections.sort(tsFilesList, new Comparator<String>() {
						
						//MIDE-068_01.mp4Frag1Num0.ts  -> 100
						//MIDE-068_01.mp4Frag11Num10.ts	--> 110
						@Override
						public int compare(String s1, String s2) {
							char start1 = s1.charAt(s1.indexOf(".mp4")-1);
							String end1 = s1.substring(s1.indexOf("Frag")+4, s1.lastIndexOf("Num"));
							int result = start1*100;
							result += Integer.valueOf(end1);
							/*-----*/
							char start2 = s2.charAt(s2.indexOf(".mp4")-1);
							String end2 = s2.substring(s2.indexOf("Frag")+4, s2.lastIndexOf("Num"));
							int result2 = start2*100;
							result2 += Integer.valueOf(end2);
							
							return result - result2;
						}
						
					});
					logger.info("排序好后的ts视频文件地址："+tsFilesList);	//排序后的list
					
					
					
					FileOutputStream fos = null;
					
					boolean success = true;
					
					try {
						logger.info("开始合并文件："+fileMP4.getName());
						
						fos = new FileOutputStream(fileMP4);
						
						for(String name : tsFilesList){
							File target = new File(path + name);
							if(!target.exists()){
								//删除MP4文件
								if(fileMP4.exists()){
									fileMP4.delete();
								}
								success = false;
								//退出这个目录的循环
								break;
							}else{
								logger.info("合并文件："+target.getName());
								//如果ts文件存在，进行合并
								FileInputStream fis = new FileInputStream(target);
								int len =0;
								byte[] buffer = new byte[100*1024];
								while((len = fis.read(buffer))!=-1){
									fos.write(buffer, 0, len);
								}
								fis.close();
							}
						}
						if(success){
							logger.info("合并文件成功："+fileMP4.getName());
						}else{
							logger.info("合并文件因为文件缺少失败："+fileMP4.getName());
						}
						
					} catch (FileNotFoundException e) {
						logger.error("文件不存在");
						e.printStackTrace();
					} catch (IOException e) {
						logger.error("读取文件错误");
						e.printStackTrace();
					} finally{
						if(fos != null){
							try {
								fos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					 
				}
				
				//仅合并一个目录
//				if(count >=1){
//					break;
//				}
			}
			
			
		}
		
	}

	@Override
	public void run() {
		logger.info("start FileMerge Task @" + new Date());
		merge();
	}
}
