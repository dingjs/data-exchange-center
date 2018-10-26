package data.exchange.center.service.zigong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.exchange.center.service.zigong.service.ZigongService;

@RestController
public class ZigongController {

	@Autowired
	private ZigongService zigongService;
	
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
	@RequestMapping(value = "/getAjbsList" ,method = RequestMethod.GET)
	public Object getAjbsList(
			@RequestParam("startDateTime")String startDateTime,
			@RequestParam("endDateTime")String endDateTime
			) throws Exception{
		return zigongService.getAjbsList(startDateTime, endDateTime);
	}
	
	
	@RequestMapping(value = "/getTqtj" ,method = RequestMethod.GET)
	public Object getTqtj(
			@RequestParam("ajbs")String ajbs,
			@RequestParam("ajlx")String ajlx) throws Exception{
		return zigongService.getTqtj(ajbs, ajlx);
	}
}
