package data.exchange.center.ommp.service.user.impl;

import org.springframework.stereotype.Service;

import data.exchange.center.ommp.service.user.HelloService;

/**
 * @Author: HanLong
 * @Date: Create in 2018/3/16 20:51
 * @Description:
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        System.out.println("greeting");
        return "hello "+name;
    }

}
