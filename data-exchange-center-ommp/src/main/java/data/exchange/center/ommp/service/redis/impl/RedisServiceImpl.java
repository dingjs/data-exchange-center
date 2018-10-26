package data.exchange.center.ommp.service.redis.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import data.exchange.center.ommp.domain.redis.Operate;
import data.exchange.center.ommp.domain.redis.RedisInfoDetail;
import data.exchange.center.ommp.service.redis.RedisService;
import data.exchange.center.ommp.util.redis.RedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

public class RedisServiceImpl implements RedisService{

    private RedisUtil redisUtil;

    public RedisServiceImpl(Jedis jedis) {
    	this.redisUtil = new RedisUtil(jedis);
    }
    
    @Override
    public List<RedisInfoDetail> getRedisInfo() {
        //获取redis服务器信息
        String info = redisUtil.getRedisInfo();
        List<RedisInfoDetail> ridList = new ArrayList<RedisInfoDetail>();
        String[] strs = info.split("\n");
        RedisInfoDetail rif = null;
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                rif = new RedisInfoDetail();
                String s = strs[i];
                String[] str = s.split(":");
                if (str != null && str.length > 1) {
                    String key = str[0];
                    String value = str[1];
                    rif.setKey(key);
                    rif.setValue(value);
                    ridList.add(rif);
                }
            }
        }
        return ridList;
    }

    @Override
    public List<Operate> getLogs(long entries) {
        List<Slowlog> list = redisUtil.getLogs(entries);
        List<Operate> opList = null;
        Operate op  = null;
        boolean flag = false;
        if (list != null && list.size() > 0) {
            opList = new LinkedList<Operate>();
            for (Slowlog sl : list) {
                String args = JSON.toJSONString(sl.getArgs());
                if (args.equals("[\"PING\"]") || args.equals("[\"SLOWLOG\",\"get\"]") || args.equals("[\"DBSIZE\"]") || args.equals("[\"INFO\"]")) {
                    continue;
                }   
                op = new Operate();
                flag = true;
                op.setId(sl.getId());
                op.setExecuteTime(getDateStr(sl.getTimeStamp() * 1000));
                op.setUsedTime(sl.getExecutionTime()/1000.0 + "ms");
                op.setArgs(args);
                opList.add(op);
            }
        } 
        if (flag) 
            return opList;
        else 
            return null;
    }
    @Override
    public Long getLogLen() {
        return redisUtil.getLogsLen();
    }

    @Override
    public String logEmpty() {
        return redisUtil.logEmpty();
    }
    @Override
    public Map<String,Object> getKeysSize() {
        long dbSize = redisUtil.dbSize();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("create_time", new Date().getTime());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String,Object> getMemeryInfo() {
        String[] strs = redisUtil.getRedisInfo().split("\n");
        Map<String, Object> map = null;
        for (int i = 0; i < strs.length; i++) {
            String s = strs[i];
            String[] detail = s.split(":");
            if (detail[0].equals("used_memory")) {
                map = new HashMap<String, Object>();
                map.put("used_memory",detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", new Date().getTime());
                break;
            }
        }
        return map;
    }
    
    private String getDateStr(long timeStmp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStmp));
    }
}