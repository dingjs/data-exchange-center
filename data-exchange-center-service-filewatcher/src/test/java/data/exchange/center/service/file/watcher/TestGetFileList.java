package data.exchange.center.service.file.watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestGetFileList {

	static List<File> fileLists = new ArrayList<File>();
	
	public static void main(String[] args) {
		System.out.println(getFileList("E:\\shiro"));
		System.out.println(fileLists);
	}
	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年9月19日 下午5:52:43
	 * @param strPath
	 * @return
	 */
	public static List<File> getFileList(String strPath) {
		List<File> fileList = new ArrayList<File>();
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					List<File> list = getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
					fileLists.addAll(list);
				} else { // 判断文件名是否以.avi结尾
					String strFileName = files[i].getAbsolutePath();
//					System.out.println("---" + strFileName);
					fileLists.add(files[i]);
				}
			}
		}
		return fileList;
	}
}
