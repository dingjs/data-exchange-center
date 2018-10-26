package data.exchange.center.monitor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/peloxinVoke")
public class PeloxinVokeController {
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model){
    	List<Object> list =new ArrayList<>();
        model.addAttribute("tree",list);
        return "/peloxinVoke/list";
    }
}
