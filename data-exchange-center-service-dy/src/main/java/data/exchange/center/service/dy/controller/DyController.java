package data.exchange.center.service.dy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import data.exchange.center.service.dy.service.DownloadService;
import data.exchange.center.service.dy.service.DyService;

@Controller
public class DyController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DownloadService downloadService;
    @Autowired
    DyService dyService;

    @RequestMapping(value = "/getFile/{bs}", method = RequestMethod.GET)
    public String downloadTemplateFile(@PathVariable String bs, HttpServletResponse response,
            HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, Object> param = new HashMap<>();
        Base64.Decoder decoder = Base64.getDecoder();
        bs = new String(decoder.decode(bs), "UTF-8");
        String[] sourceStrArray = bs.split("_");
        String ajbs = sourceStrArray[1];
        String xh = sourceStrArray[2];
        param.put("ajbs", ajbs);
        param.put("xh", xh);
        param = dyService.getFileName(param);
        int flag = Integer.valueOf(String.valueOf( param.get("v_cursor2")));
        String filename = String.valueOf(param.get("v_cursor1"));
        String ret = String.valueOf(param.get("v_cursor3"));
        if(flag !=0){
        int lastIndexOfPoint = filename.lastIndexOf(".");
        String suffix = filename.substring(lastIndexOfPoint);

        // 需要下载的文件
        // String filepath =
        // request.getSession().getServletContext().getRealPath(filename);
        // File myfile = new File(filepath);

        byte[] in = downloadService.download(ajbs+"_"+xh);
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + filename.getBytes() + suffix);
        response.addHeader("Content-Length", "" + in.length);
        response.setContentType("application/octet-stream");

        OutputStream toClient = null;
        InputStream fis = null;
        // 打开文件输入流和 servlet输出流
        try {
            InputStream input = new ByteArrayInputStream(in);
            toClient = new BufferedOutputStream(response.getOutputStream());
            fis = new BufferedInputStream(input);
            IOUtils.copy(fis, toClient);
            toClient.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(toClient);
        }
        }
        return null;
    }
}
