package data.exchange.center.ommp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@PreAuthorize("hasRole('ROLE_ADMINe')")
	@RequestMapping("/test")
	public Object test() {
		return "test";
	}
}
