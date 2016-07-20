package com.tiny.entity;

import java.io.File;
import java.text.DecimalFormat;

import javax.swing.filechooser.FileSystemView;

import com.ezcloud.framework.util.StringUtil;
import com.ezcloud.framework.vo.DataSet;

public class Test {

	public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	
	 /**
     * 获取硬盘的每个盘符
     */
    @SuppressWarnings("unchecked")
	public static DataSet getAllDrivers(){
    	DataSet ds =new DataSet();
        // 当前文件系统类
        FileSystemView fsv = FileSystemView.getFileSystemView();
        // 列出所有windows 磁盘
        File[] fs = File.listRoots();
        // 显示磁盘卷标
        for (int i = 0; i < fs.length; i++) {
            String filePath =fsv.getSystemDisplayName(fs[i]);
            System.out.println( filePath );
//            listFolder( filePath );
            System.out.print("总大小" + FormetFileSize(fs[i].getTotalSpace()));
            System.out.println("剩余" + FormetFileSize(fs[i].getFreeSpace()));
            ds.add(filePath);
        }
        return ds;
    }
    
    /**
     * 读取某个目录下的全部非隐藏目录
     * @param path
     */
    @SuppressWarnings("unchecked")
	public static DataSet listFolder( String path ){
    	DataSet ds=new DataSet();
    	if (StringUtil.isEmptyOrNull(path)) {
			ds =getAllDrivers();
			return ds;
		}
    	File file =new File( path );
    	if (file.isDirectory()) {
			File chdFiles[] =file.listFiles();
			File tmpFile  =null;
			for (int i = 0; chdFiles!=null && i <  chdFiles.length; i++) {
				tmpFile =chdFiles[i];
				if ( tmpFile.isDirectory() && ! tmpFile.isHidden()) {
					String tmpFilePath =tmpFile.getPath();
					System.out.println("-------folder:"+ tmpFilePath );
					ds.add(tmpFilePath);
//					listFile( tmpFilePath );
				}
			}
		}
    	return ds;
    }
    
	public static void main(String[] args) {
//		getAllDrivers();
		listFolder(null);
		listFolder("/");
	}
}
