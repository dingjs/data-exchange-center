package data.exchange.center.service.wuhou.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.service.wuhou.service.WuhouService;

@RestController
public class WuhouController {

    @Autowired
    private WuhouService wuhouService;

    /**
     * 
     * @function 获取案件列表
     * @author wenyuguang
     * @creaetime 2017年12月12日 上午10:30:34
     * @param ajbs
     * @param fydm
     * @param ajlx
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAjbsList", method = RequestMethod.GET)
    public Object getAjbsList(@RequestParam("startDateTime") String startDateTime,
            @RequestParam("endDateTime") String endDateTime, @RequestParam("fydm") String fydm) throws Exception {
        return wuhouService.getAjbsList(startDateTime, endDateTime, fydm);
    }

    /**
     * 
     * @function 案件信息 （限定于民事一审）
     * @author wenyuguang
     * @creaetime 2017年12月11日 上午10:51:11
     * @param ajbs
     * @param fydm
     * @param ajlx
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAjbsInfo", method = RequestMethod.GET)
    public Object getAjbsInfo(@RequestParam("ajbs") String ajbs, @RequestParam("fydm") String fydm,
            @RequestParam("ajlx") String ajlx) throws Exception {
        return wuhouService.getAjbsInfo(ajbs, fydm, ajlx);
    }

    /**
     * 
     * @function 获取案件材料列表
     * @author wenyuguang
     * @creaetime 2017年12月11日 上午11:10:34
     * @param ajbs
     * @param fydm
     * @param ajlx
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAjclList", method = RequestMethod.GET)
    public Object getAjclList(@RequestParam("ajbs") String ajbs, @RequestParam("fydm") String fydm,
            @RequestParam("ajlx") String ajlx) throws Exception {
        return wuhouService.getAjclList(ajbs, fydm, ajlx);
    }

    /**
     * 
     * @function 获取案件材料(可能为blob,ftp)
     * @author wenyuguang
     * @creaetime 2017年12月11日 上午11:17:40
     * @param ajbs
     * @param xh
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAjcl", method = RequestMethod.GET)
    public Object getAjcl(@RequestParam("ajbs") String ajbs, @RequestParam("xh") String xh) throws Exception {
        return wuhouService.getAjcl(ajbs, xh);
    }

    /**
     * 
     * @function 获取用户信息（根据6位法院代码）
     * @author wenyuguang
     * @creaetime 2017年12月11日 上午11:24:49
     * @param fydm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Object getUserInfo(@RequestParam("fydm") String fydm) throws Exception {
        return wuhouService.getUserInfo(fydm);
    }

    /**
     * 
     * @function 获取部门信息（每一次都是全量）
     * @author wenyuguang
     * @creaetime 2017年12月11日 上午11:26:12
     * @param fydm
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBmInfo", method = RequestMethod.GET)
    public Object getBmInfo() throws Exception {
        return wuhouService.getBmInfo();
    }

    /**
     * @Title: setDbxx @Description: 文书回填（督办信息） @param @param
     *         dbxx @param @return 参数 @return String 返回类型 @throws
     */
    @RequestMapping(value = "/reviewResults", method = RequestMethod.GET)
    public String reviewResults(@RequestParam String xml) {
        try {
//            Base64.Encoder encoder = Base64.getEncoder();
//            byte[] textByte = xml.getBytes("UTF-8");
//            xml = encoder.encodeToString(textByte);
            System.out.println("回写督办信息" + xml);
            return wuhouService.setDbxx(xml);
        } catch (Exception e) {
            return "数据转换出现错误";
        }
    }
}
