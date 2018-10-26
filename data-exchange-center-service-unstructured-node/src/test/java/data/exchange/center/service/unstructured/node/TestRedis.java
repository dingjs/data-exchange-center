package data.exchange.center.service.unstructured.node;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import data.exchange.center.common.constant.Constant;
import redis.clients.jedis.Jedis;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("150.0.2.164");
        jedis.auth("sgyRedis");
        Object obj = unserialize(jedis.get(("300000000020063" + Constant.C_EAJ_SSJCYX).getBytes()));
        List<ConcurrentMap<String, Object>>  lista = (List<ConcurrentMap<String, Object>>) obj;
        for (int i = 0; i < lista.size(); i++) {
            ConcurrentMap<String, Object> map = lista.get(i);
            System.out.println(map);
        }
    }

    public static Object unserialize(byte[] bytes) {
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (bytes != null) {
                bis = new ByteArrayInputStream(bytes);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (bis != null)
                    bis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }
}
