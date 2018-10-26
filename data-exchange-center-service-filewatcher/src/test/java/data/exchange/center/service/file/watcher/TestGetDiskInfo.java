package data.exchange.center.service.file.watcher;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGetDiskInfo {

	private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

	/**
	 * 获取磁盘使用信息
	 * 
	 * @return
	 */
	public static List<Map<String, String>> getInfo() {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		File[] roots = File.listRoots();// 获取磁盘分区列表
		for (File file : roots) {
			Map<String, String> map = new HashMap<String, String>();

			long freeSpace = file.getFreeSpace();
			long totalSpace = file.getTotalSpace();
			long usableSpace = totalSpace - freeSpace;

			map.put("path", file.getPath());
			map.put("freeSpace", freeSpace / 1024 / 1024 / 1024 + "G");// 空闲空间
			map.put("usableSpace", usableSpace / 1024 / 1024 / 1024 + "G");// 可用空间
			map.put("totalSpace", totalSpace / 1024 / 1024 / 1024 + "G");// 总空间
			map.put("percent", DECIMALFORMAT.format(((double) usableSpace / (double) totalSpace) * 100) + "%");// 总空间

			list.add(map);
		}
		return list;
	}

	public static void main(String[] args) {

//		System.out.println(getInfo());
		File[] roots = File.listRoots();// 获取磁盘分区列表
		for (File disk : roots) {
			if(disk.getPath().toLowerCase().startsWith("d")) {
				System.out.println(disk.getFreeSpace() / 1024 / 1024 / 1024);
				if(disk.getFreeSpace() / 1024 / 1024 / 1024 < 30) {
					File dir = new File("D:\\FYFTP\\sgy\\zip");
					File[] files = dir.listFiles();
					files[0].delete();
				}
			}
		}
        List<Integer> nums = new ArrayList<Integer>();  
        nums.add(3);
        nums.add(5);
        nums.add(1);
        nums.add(0);
        System.out.println(nums);
        Collections.sort(nums);
        System.out.println(nums);

	}
}
