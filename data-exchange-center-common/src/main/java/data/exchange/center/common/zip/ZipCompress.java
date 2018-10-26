package data.exchange.center.common.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * Description:
 * <p>Company: xinya </p>
 * <p>Date:2017年11月1日 下午3:34:54</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
public class ZipCompress {
	private String zipFileName; // 目的地Zip文件
	private String sourceFileName; // 源文件（带压缩的文件或文件夹）

	/**
	 * 
	 * @param zipFileName
	 * @param sourceFileName
	 */
	public ZipCompress(String zipFileName, String sourceFileName) {
		this.zipFileName = zipFileName;
		this.sourceFileName = sourceFileName;
	}

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年11月3日 上午10:32:32
	 * @throws Exception
	 */
	public void zip() throws Exception {
		System.out.println("压缩中...");
		
		ZipOutputStream out = null;
		BufferedOutputStream bos = null;
		try {
			// 创建zip输出流
			out = new ZipOutputStream(new FileOutputStream(zipFileName));
			// 创建缓冲输出流
			bos = new BufferedOutputStream(out);
			File sourceFile = new File(sourceFileName);
			// 调用函数
			compress(out, bos, sourceFile, null);
		}catch(Exception e) {
			throw e;
		}finally {
			bos.close();
			out.close();
		}
		System.out.println("压缩完成");
	}

	/**
	 * 
	 * @function 
	 * @author wenyuguang
	 * @creaetime 2017年11月3日 上午10:32:28
	 * @param out
	 * @param bos
	 * @param sourceFile
	 * @param base
	 * @throws Exception
	 */
	public void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {

			// 取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();

			if (flist.length == 0)// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			{
				System.out.println(base + "/");
				out.putNextEntry(new ZipEntry(base + "/"));
			} else{// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (int i = 0; i < flist.length; i++) {
					if (base != null) {
						compress(out, bos, flist[i], base + "/" + flist[i].getName());
					} else {
						compress(out, bos, flist[i], flist[i].getName());
					}
				}
			}
		} else{// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
			out.putNextEntry(new ZipEntry(base));
			FileInputStream fos = null;
			BufferedInputStream bis = null;
			try {
				fos = new FileInputStream(sourceFile);
				bis = new BufferedInputStream(fos);
				
				System.out.println(base);
				// 将源文件写入到zip文件中
				byte[] bufs = new byte[2048];
				int read = 0;
				while ((read = bis.read(bufs, 0, 2048)) != -1) {
					out.write(bufs, 0, read);
				}
			}catch(Exception e) {
				throw e;
			}finally {
				bis.close();
				fos.close();
			}
		}
	}

	public static void main(String[] args) {
		ZipCompress zipCom = new ZipCompress(
				"f:\\testettt.zip",
				"f:\\testettt");
		try {
			zipCom.zip();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}