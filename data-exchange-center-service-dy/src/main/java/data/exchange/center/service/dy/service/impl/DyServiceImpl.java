package data.exchange.center.service.dy.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.exchange.center.service.dy.mapper.DyMapper;
import data.exchange.center.service.dy.service.DyService;
@Service
public class DyServiceImpl implements DyService {
    @Autowired
    DyMapper dyMapper;

    @Override
    public Map<String, Object> getFileName(Map<String, Object> param) {
        dyMapper.getFileName(param);
        return param;
    }

}
