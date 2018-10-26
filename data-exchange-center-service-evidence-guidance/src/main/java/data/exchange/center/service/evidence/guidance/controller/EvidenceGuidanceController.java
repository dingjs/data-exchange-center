package data.exchange.center.service.evidence.guidance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.evidence.guidance.service.EvidenceGuidanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * Description:证据指引服务接口
 * <p>Company: xinya </p>
 * <p>Date:2017年10月27日 下午4:47:54</p>
 * @author Wen.Yuguang
 * @version 1.0
 *
 */
@RestController
@Api("证据指引服务接口")
public class EvidenceGuidanceController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EvidenceGuidanceService evidenceGuidanceService;
	
	@ApiOperation("证据指引校验")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType="query",name="ajbh",dataType="String",required=true,value="案件编号",defaultValue="案件编号"),
    	@ApiImplicitParam(paramType="query",name="ajlb",dataType="String",required=true,value="案件类别",defaultValue="采用标准的案件类别代码表"),
    	@ApiImplicitParam(paramType="query",name="ajzt",dataType="String",required=true,value="案件状态",defaultValue="采用标准的案件状态代码表"),
    	@ApiImplicitParam(paramType="query",name="userinfo",dataType="String[]",required=true,value="用户信息",defaultValue="用户信息，指调用证据指引服务的使用者信息。\r\n" + 
    			"参数使用规则如下：\r\n" + 
    			"[身份证号，姓名，部门代码，机构代码]\r\n" + 
    			"Array [0]身份证号\r\n" + 
    			"Array [1]姓名\r\n" + 
    			"Array [2]部门代码(部门代码参见附录A部门代码CODE_BMDM)\r\n" + 
    			"Array [3]机构编码\r\n" + 
    			"Array [4]IP地址\r\n" + 
    			"Array [5]MAC\r\n" + 
    			"Array [6]授权登陆名\r\n" + 
    			"Array [7]授权登陆密码\r\n" + 
    			"Array [8]请求方编号")
    })
    @ApiResponses({
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
	@RequestMapping(value = "/upCaseEvidence" ,method = RequestMethod.GET)
	public Object upCaseEvidence(
			@RequestParam("ajbh") String ajbh, 
			@RequestParam("ay")   String ay, 
			@RequestParam("ajzt") String ajzt) {
		String[] userInfo = {"410329198410252633","王进伟","0300","51000010","150.0.2.167","6C-92-BF-36-A0-38","SC5101_0001_YHM","SC5101_0001_MM","SC5101_0001"};
		try {
			/**
			 * 2000表示法院立案
			 * 案件性质默认留空
			 * 案件类别对应为案由
			 */
			return evidenceGuidanceService.UPCaseEvidence(ajbh, ay, "", ajzt, userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用证据指引出错"+e.getMessage());
			return e.getMessage();
		}
	}
}
