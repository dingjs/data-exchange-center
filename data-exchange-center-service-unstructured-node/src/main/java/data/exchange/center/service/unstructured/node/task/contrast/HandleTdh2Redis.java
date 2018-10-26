package data.exchange.center.service.unstructured.node.task.contrast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import data.exchange.center.common.constant.Constant;
import data.exchange.center.service.unstructured.node.domain.TempEajJz;
import data.exchange.center.service.unstructured.node.domain.TempEajJzwjAllNew;
import data.exchange.center.service.unstructured.node.domain.TempEajSsjcyx;
import data.exchange.center.service.unstructured.node.util.DecodeUtil;
import data.exchange.center.service.unstructured.node.util.DeflaterUtil;
import data.exchange.center.service.unstructured.node.util.SerializationUtil;
import data.exchange.center.service.unstructured.node.util.VeDate;
import oracle.sql.BLOB;

/**
 * @ClassName: HandleTdh2Redis
 * @Description:数据对比类
 * @author: BaiMaojun
 * @Date: 2018年3月8日 下午5:18:48
 */
public class HandleTdh2Redis {
    private static Logger logger = LoggerFactory.getLogger(HandleTdh2Redis.class);

    /**
     * @Title: jzwjAllnewDate @Description: JZWJ_ALL_NEW对比 @param @param
     *         listMapTempEaj @param @param tableName @param @param
     *         ajbs @param @param redisTemplate @param @return @param @throws
     *         Exception 参数 @return List<TempEajJzwjAllNew> 返回类型 @throws
     */
    @SuppressWarnings({ "unchecked" })
    public static List<TempEajJzwjAllNew> jzwjAllnewDate(List<TempEajJzwjAllNew> listMapTempEaj, String ajbs,
            RedisTemplate<Object, Object> redisTemplate) throws Exception {

        List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
        List<TempEajJzwjAllNew> listTempEajJzwjAllNew = Collections.synchronizedList(new ArrayList<>());
        ConcurrentMap<String, Object> tempEajMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> redisMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> data = new ConcurrentHashMap<String, Object>();
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        String bh = ajbs + Constant.C_EAJ_JZWJ_ALL_NEW;// 组装序号
        try {
            // 从redis中获取数据
            try {
                if (redisTemplate.hasKey(bh)) {
                    Object redisDate = operations.get(bh);
                    //用压缩后的数据替换原有数据
                    if (redisDate instanceof java.util.List) {
                        listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
                        jzwjAllNewToRedis(listMapTempEaj, redisTemplate, ajbs);
                    } else {
                        byte[] b = DeflaterUtil.uncompress((byte[]) operations.get(bh));
                        listRedis = (List<ConcurrentMap<String, Object>>) SerializationUtil.deserialize(b);
                    }

                }
                System.setProperty(Constant.C_REDISTATE, Constant.C_NORMAL_CONNECT);
            } catch (Exception e) {
                e.printStackTrace();
                System.setProperty(Constant.C_REDISTATE, Constant.C_ERROR_CONNECT);
                logger.error("ajbs:" + ajbs + Constant.C_TEMP_EAJ_JZWJ_ALL_NEW + "对比数据出现错误：" + e.getMessage());
            }

            // 判断redis里面是否存在业务库中没有的数据
            for (int i = 0; i < listMapTempEaj.size(); i++) {
                TempEajJzwjAllNew tempEajJzwjAllNew = listMapTempEaj.get(i);
                tempEajMap.put(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH(),
                        Constant.C_TEMP_EAJ_JZWJ_ALL_NEW);
            }
            // 在redis中遍历出需要删除的数据
            for (int i = 0; i < listRedis.size(); i++) {
                data = new ConcurrentHashMap<String, Object>();
                data = listRedis.get(i);
                redisMap.put(data.get("XH") + "_" + data.get("YXXH"), data);
                if (tempEajMap.get(data.get("XH") + "_" + data.get("YXXH")) == null) {
                    TempEajJzwjAllNew tempEajJzwj = new TempEajJzwjAllNew();
                    tempEajJzwj.setAHDM(ajbs);
                    tempEajJzwj.setXH((String) data.get("XH"));
                    tempEajJzwj.setAJLX((String) data.get("AJLX"));
                    tempEajJzwj.setACTIONTYPE(Constant.C_TYPE_DELETE);
                    tempEajJzwj.setYXXH(Integer.valueOf((String) data.get("YXXH")));
                    listTempEajJzwjAllNew.add(tempEajJzwj);
                }
            }

            // 遍历出需要更新的案件
            for (int i = 0; i < listMapTempEaj.size(); i++) {
                TempEajJzwjAllNew tempEajJzwjAllNew = listMapTempEaj.get(i);
                // 零时判断，如果更新时间为空就不处理
                if (!StringUtils.isEmpty(tempEajJzwjAllNew.getLASTUPDATE())) {
                    // 如果在redis中没有获取到数据，就是新增
                    if (redisMap.get(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH()) == null) {
                        tempEajJzwjAllNew.setACTIONTYPE(Constant.C_TYPE_INSERT);
                        listTempEajJzwjAllNew.add(tempEajJzwjAllNew);
                        // 如果时间不相等就更新
                    } else if (redisMap.get(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH()) != null) {
                        data = new ConcurrentHashMap<String, Object>();
                        data = (ConcurrentMap<String, Object>) redisMap
                                .get(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH());
                        if (data.get("LASTUPDATE") != null
                                && !VeDate.strDateToDate(String.valueOf(data.get("LASTUPDATE"))).equals(
                                        VeDate.strDateToDate(String.valueOf(tempEajJzwjAllNew.getLASTUPDATE())))) {

                            tempEajJzwjAllNew.setACTIONTYPE(Constant.C_TYPE_UPDATE);
                            listTempEajJzwjAllNew.add(tempEajJzwjAllNew);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ajbs:" + ajbs + Constant.C_TEMP_EAJ_JZWJ_ALL_NEW + "对比数据出现错误：" + e.getMessage());
            e.printStackTrace();
        }
        return listTempEajJzwjAllNew;
    }

    /**
     * @Title: jzDate @Description:
     *         eaj_jz数据对比（包含需要根据TEMP_EAJ_JZWJ_ALL_NEW更新数据） @param @param
     *         listMapTempEaj @param @param tableName @param @param
     *         ajbs @param @param redisTemplate @param @param
     *         listTempEajJzwjAllNew @param @return @param @throws Exception
     *         参数 @return List<TempEajJz> 返回类型 @throws
     */
    @SuppressWarnings({ "unchecked" })
    public static List<TempEajJz> jzDate(List<TempEajJz> listMapTempEaj, String ajbs,
            RedisTemplate<Object, Object> redisTemplate) throws Exception {

        List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
        ConcurrentMap<String, Object> tempEajMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> redisMap = new ConcurrentHashMap<String, Object>();
        List<TempEajJz> listTempEajJz = Collections.synchronizedList(new ArrayList<>());
        ConcurrentMap<String, Object> data = new ConcurrentHashMap<String, Object>();
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();

        String bh = ajbs + Constant.C_EAJ_JZ;// 组装序号

        try {
            // 从redis中获取数据
            if (redisTemplate.hasKey(bh)) {
                Object redisDate = operations.get(bh);
                //用压缩后的数据替换原有数据
                if (redisDate instanceof java.util.List) {
                    listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
                    jzToRedis(listMapTempEaj, redisTemplate, ajbs);
                } else {
                    byte[] b = DeflaterUtil.uncompress((byte[]) operations.get(bh));
                    listRedis = (List<ConcurrentMap<String, Object>>) SerializationUtil.deserialize(b);
                }
            }
            // 判断redis里面是否存在业务库中没有的数据
            for (int i = 0; i < listMapTempEaj.size(); i++) {
                TempEajJz tempEajJz = listMapTempEaj.get(i);
                tempEajMap.put(tempEajJz.getXH(), Constant.C_TEMP_EAJ_JZ);
            }
            // 在redis中遍历出需要删除的数据
            for (int i = 0; i < listRedis.size(); i++) {
                data = new ConcurrentHashMap<String, Object>();
                data = listRedis.get(i);
                redisMap.put(String.valueOf(data.get("XH")), data);
                if (tempEajMap.get(String.valueOf(data.get("XH"))) == null) {
                    TempEajJz tempEajJOne = new TempEajJz();
                    tempEajJOne.setAHDM(ajbs);
                    tempEajJOne.setXH((String) data.get("XH"));
                    tempEajJOne.setAJLX((String) data.get("AJLX"));
                    tempEajJOne.setACTIONTYPE(Constant.C_TYPE_DELETE);
                    listTempEajJz.add(tempEajJOne);
                }
            }

            // 遍历出需要更新的案件
            for (int i = 0; i < listMapTempEaj.size(); i++) {
                TempEajJz tempEajJz = listMapTempEaj.get(i);
                // 零时判断，如果更新时间为空就不处理
                if (!StringUtils.isEmpty(tempEajJz.getLASTUPDATE())) {
                    // 如果在redis中没有获取到数据，就是新增
                    if (redisMap.get(tempEajJz.getXH()) == null) {
                        tempEajJz.setACTIONTYPE(Constant.C_TYPE_INSERT);
                        listTempEajJz.add(tempEajJz);
                        // 如果时间不相等就更新
                    } else if (redisMap.get(tempEajJz.getXH()) != null) {
                        data = new ConcurrentHashMap<String, Object>();
                        data = (ConcurrentMap<String, Object>) redisMap.get(tempEajJz.getXH());
                        if (data.get("LASTUPDATE") != null
                                && !VeDate.strDateToDate(String.valueOf(data.get("LASTUPDATE")))
                                        .equals(VeDate.strDateToDate(String.valueOf(tempEajJz.getLASTUPDATE())))) {

                            tempEajJz.setACTIONTYPE(Constant.C_TYPE_UPDATE);
                            listTempEajJz.add(tempEajJz);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ajbs:" + ajbs + Constant.C_TEMP_EAJ_JZ + "对比数据出现错误：" + e.getMessage());
            e.printStackTrace();
        }
        return listTempEajJz;
    }

    @SuppressWarnings({ "unchecked" })
    public static List<TempEajSsjcyx> ssjcyxDate(List<TempEajSsjcyx> listMapTempEaj, String ajbs, String fydm,
            RedisTemplate<Object, Object> redisTemplate) throws Exception {

        List<ConcurrentMap<String, Object>> listRedis = Collections.synchronizedList(new ArrayList<>());
        List<TempEajSsjcyx> listTempEajSsjcyx = Collections.synchronizedList(new ArrayList<>());
        ConcurrentMap<String, Object> tempEajMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> redisMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> data = new ConcurrentHashMap<String, Object>();
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();

        String bh = ajbs + Constant.C_EAJ_SSJCYX;// 组装序号

        try {
            // 从redis中获取数据
            if (redisTemplate.hasKey(bh)) {
                Object redisDate = operations.get(bh);
                //用压缩后的数据替换原有数据
                if (redisDate instanceof java.util.List) {
                    listRedis = (List<ConcurrentMap<String, Object>>) operations.get(bh);
                    ssjcyxToRedis(listMapTempEaj, redisTemplate, ajbs);
                } else {
                    byte[] b = DeflaterUtil.uncompress((byte[]) operations.get(bh));
                    listRedis = (List<ConcurrentMap<String, Object>>) SerializationUtil.deserialize(b);
                }
            }
            // 判断redis里面是否存在业务库中没有的数据
            for (int i = 0; i < listMapTempEaj.size(); i++) {
                TempEajSsjcyx tempEajSsjcyx = listMapTempEaj.get(i);
                tempEajMap.put(String.valueOf(tempEajSsjcyx.getXH()), Constant.C_TEMP_EAJ_SSJCYX);
            }
            // 在redis中遍历出需要删除的数据
            for (int i = 0; i < listRedis.size(); i++) {
                data = new ConcurrentHashMap<String, Object>();
                data = listRedis.get(i);
                redisMap.put(String.valueOf(data.get("XH")), data);
                if (tempEajMap.get(String.valueOf(data.get("XH"))) == null) {
                    TempEajSsjcyx tempEajSsjcyxOne = new TempEajSsjcyx();
                    tempEajSsjcyxOne.setAJBS(ajbs);
                    tempEajSsjcyxOne.setXH((Float) data.get("XH"));
                    tempEajSsjcyxOne.setAJLX((String) data.get("AJLX"));
                    tempEajSsjcyxOne.setACTIONTYPE(Constant.C_TYPE_DELETE);
                    tempEajSsjcyxOne.setFYDM(fydm);
                    listTempEajSsjcyx.add(tempEajSsjcyxOne);
                }
            }

            // 遍历出需要更新的案件
            for (int i = 0; i < listMapTempEaj.size(); i++) {
                TempEajSsjcyx tempEajSsjcyx = listMapTempEaj.get(i);
                // 零时判断，如果更新时间为空就不处理
                if (!StringUtils.isEmpty(tempEajSsjcyx.getLASTUPDATE())) {
                    // 如果在redis中没有获取到数据，就是新增
                    if (redisMap.get(String.valueOf(tempEajSsjcyx.getXH())) == null) {
                        tempEajSsjcyx.setACTIONTYPE(Constant.C_TYPE_INSERT);
                        tempEajSsjcyx.setFYDM(fydm);
                        listTempEajSsjcyx.add(tempEajSsjcyx);
                        // 如果时间不相等就更新
                    } else if (redisMap.get(String.valueOf(tempEajSsjcyx.getXH())) != null) {
                        data = new ConcurrentHashMap<String, Object>();
                        data = (ConcurrentMap<String, Object>) redisMap.get(String.valueOf(tempEajSsjcyx.getXH()));
                        if (data.get("LASTUPDATE") != null
                                && !VeDate.strDateToDate(String.valueOf(data.get("LASTUPDATE")))
                                        .equals(VeDate.strDateToDate(String.valueOf(tempEajSsjcyx.getLASTUPDATE())))) {

                            tempEajSsjcyx.setACTIONTYPE(Constant.C_TYPE_UPDATE);
                            tempEajSsjcyx.setFYDM(fydm);
                            listTempEajSsjcyx.add(tempEajSsjcyx);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ajbs:" + ajbs + Constant.C_TEMP_EAJ_SSJCYX + "对比数据出现错误：" + e.getMessage());
            e.printStackTrace();
        }
        return listTempEajSsjcyx;
    }

    /**
     * @Title: jzToJzwjAllnew @Description:
     *         找出需要根据jz更新TempEajJzwjAllNew的数据 @param @param listMapTempEaj
     *         所有数据 @param @param listTempEajJzwjAllNew 对比后需要更新的数据 @param @param
     *         listTempEajJz 对比后需要更新的数据 @param @return 参数 @return
     *         TempEajJzwjAllNew 返回类型 @throws
     */
    public static List<TempEajJzwjAllNew> jzToJzwjAllnew(List<TempEajJzwjAllNew> listMapTempEaj,
            List<TempEajJzwjAllNew> listTempEajJzwjAllNew, List<TempEajJz> listTempEajJz, String fydm)
            throws Exception {

        ConcurrentMap<String, Object> jzMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> jzwjAllnewMap = new ConcurrentHashMap<String, Object>();

        List<TempEajJzwjAllNew> listData = new ArrayList<>();
        for (int i = 0; i < listTempEajJz.size(); i++) {
            TempEajJz tempEajJz = listTempEajJz.get(i);
            if (tempEajJz.getNR() != null) {
            }
            {
                jzMap.put(tempEajJz.getXH(), Constant.C_TEMP_EAJ_JZ);
            }
        }
        // 根据jz更新TempEajJzwjAllNew的数据
        for (int i = 0; i < listMapTempEaj.size(); i++) {
            TempEajJzwjAllNew tempEajJzwjAllNew = listMapTempEaj.get(i);
            if (jzMap.get(tempEajJzwjAllNew.getXH()) != null
                    && jzwjAllnewMap.get(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH()) == null) {
                jzwjAllnewMap.put(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH(),
                        Constant.C_TEMP_EAJ_JZWJ_ALL_NEW);
                tempEajJzwjAllNew.setFYDM(fydm);
                listData.add(tempEajJzwjAllNew);
            }
        }
        // 排除需要根据jz更新TempEajJzwjAllNew的数据和原本就需要更新数据的冲突
        for (int i = 0; i < listTempEajJzwjAllNew.size(); i++) {
            TempEajJzwjAllNew tempEajJzwjAllNew = listTempEajJzwjAllNew.get(i);
            if (jzwjAllnewMap.get(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH()) == null
                    && jzMap.get(tempEajJzwjAllNew.getXH()) != null) {
                jzwjAllnewMap.put(tempEajJzwjAllNew.getXH() + "_" + tempEajJzwjAllNew.getYXXH(),
                        Constant.C_TEMP_EAJ_JZWJ_ALL_NEW);
                tempEajJzwjAllNew.setFYDM(fydm);
                listData.add(tempEajJzwjAllNew);
            }
        }
        return listData;
    }

    /**
     * @Title: JzwjAllnewToJz @Description: 找出需要根据jzwj更新jz数据 @param @param
     *         listTempEaj 全量数据 @param @param listTempEajJz 更新后的数据 @param @param
     *         listTempEajJzwjAllNew 更新后的数据 @param @return 参数 @return
     *         List<TempEajJz> 返回类型 @throws
     */
    public static List<TempEajJz> JzwjAllnewToJz(List<TempEajJz> listTempEaj, List<TempEajJz> listTempEajJz,
            List<TempEajJzwjAllNew> listTempEajJzwjAllNew, String fydm) throws Exception {
        ConcurrentMap<String, Object> jzwjAllnewMap = new ConcurrentHashMap<String, Object>();
        ConcurrentMap<String, Object> jzMap = new ConcurrentHashMap<String, Object>();
        List<TempEajJz> listData = new ArrayList<>();
        for (int i = 0; i < listTempEajJzwjAllNew.size(); i++) {
            TempEajJzwjAllNew tempEajJzwjAllNew = listTempEajJzwjAllNew.get(i);
            jzwjAllnewMap.put(tempEajJzwjAllNew.getXH(), Constant.C_TEMP_EAJ_JZWJ_ALL_NEW);
        }
        for (int i = 0; i < listTempEaj.size(); i++) {
            TempEajJz tempEajJz = listTempEaj.get(i);
            if (jzwjAllnewMap.get(tempEajJz.getXH()) != null) {
                tempEajJz.setFYDM(fydm);
                listData.add(blobToBytes(tempEajJz));
                jzMap.put(tempEajJz.getXH(), Constant.C_TEMP_EAJ_JZ);
            }
        }
        for (int i = 0; i < listTempEajJz.size(); i++) {
            TempEajJz tempEajJz = listTempEajJz.get(i);
            if (jzMap.get(tempEajJz.getXH()) == null) {
                tempEajJz.setFYDM(fydm);
                listData.add(blobToBytes(tempEajJz));
            }
        }
        return listData;
    }

    /**
     * @Title: jzToRedis @Description: 写入redis @param @param
     *         listTempEajJz @param @param redisTemplate @param @param ajbs
     *         参数 @return void 返回类型 @throws
     */
    public static void jzToRedis(List<TempEajJz> listTempEajJz, RedisTemplate<Object, Object> redisTemplate,
            String ajbs) throws Exception {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        List<ConcurrentMap<String, Object>> redisList = Collections.synchronizedList(new ArrayList<>());
        String bh = ajbs + Constant.C_EAJ_JZ;
        for (int i = 0; i < listTempEajJz.size(); i++) {
            TempEajJz tempEajJz = listTempEajJz.get(i);
            if (!StringUtils.isEmpty(tempEajJz.getLASTUPDATE())) {
                ConcurrentMap<String, Object> redisMap = new ConcurrentHashMap<String, Object>();
                redisMap.put("XH", tempEajJz.getXH());
                redisMap.put("LASTUPDATE", tempEajJz.getLASTUPDATE());
                redisList.add(redisMap);
            }
        }
        operations.set(bh,DeflaterUtil.compress(SerializationUtil.serialize(redisList)));
    }

    /**
     * @Title: jzwjAllNewToRedis @Description: 写入redis @param @param
     *         listTempEajJzwjAllNew @param @param redisTemplate @param @param
     *         ajbs 参数 @return void 返回类型 @throws
     */
    public static void jzwjAllNewToRedis(List<TempEajJzwjAllNew> listTempEajJzwjAllNew,
            RedisTemplate<Object, Object> redisTemplate, String ajbs) throws Exception {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        List<ConcurrentMap<String, Object>> redisList = Collections.synchronizedList(new ArrayList<>());
        String bh = ajbs + Constant.C_EAJ_JZWJ_ALL_NEW;
        for (int i = 0; i < listTempEajJzwjAllNew.size(); i++) {
            ConcurrentMap<String, Object> redisMap = new ConcurrentHashMap<String, Object>();
            TempEajJzwjAllNew tempEajJzwjAllNew = listTempEajJzwjAllNew.get(i);
            if (!StringUtils.isEmpty(tempEajJzwjAllNew.getLASTUPDATE())) {
                redisMap.put("XH", tempEajJzwjAllNew.getXH());
                redisMap.put("YXXH", tempEajJzwjAllNew.getYXXH());
                redisMap.put("LASTUPDATE", tempEajJzwjAllNew.getLASTUPDATE());
                redisList.add(redisMap);
            }
        }
        operations.set(bh,DeflaterUtil.compress(SerializationUtil.serialize(redisList)));
    }

    /**
     * @Title: ssjcyxToRedis @Description: 写入redis @param @param
     *         listTempEajJzwjAllNew @param @param redisTemplate @param @param
     *         ajbs 参数 @return void 返回类型 @throws
     */
    public static void ssjcyxToRedis(List<TempEajSsjcyx> listTempEajJzwjAllNew,
            RedisTemplate<Object, Object> redisTemplate, String ajbs) throws Exception {
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        List<ConcurrentMap<String, Object>> redisList = Collections.synchronizedList(new ArrayList<>());
        String bh = ajbs + Constant.C_EAJ_SSJCYX;
        for (int i = 0; i < listTempEajJzwjAllNew.size(); i++) {
            TempEajSsjcyx tempEajSsjcyx = listTempEajJzwjAllNew.get(i);
            if (tempEajSsjcyx.getLASTUPDATE() != null) {
                ConcurrentMap<String, Object> redisMap = new ConcurrentHashMap<String, Object>();
                redisMap.put("XH", tempEajSsjcyx.getXH());
                redisMap.put("LASTUPDATE", tempEajSsjcyx.getLASTUPDATE());
                redisList.add(redisMap);
            }
        }
        operations.set(bh,DeflaterUtil.compress(SerializationUtil.serialize(redisList)));
    }

    /**
     * @Title: blobToBytes @Description: blob解码和转换 @param @param
     *         tempEajJz @param @return 参数 @return TempEajJz 返回类型 @throws
     */
    public static TempEajJz blobToBytes(TempEajJz tempEajJz) throws Exception {
        if (tempEajJz.getNR() instanceof oracle.sql.BLOB || tempEajJz.getNR() == null) {
            byte[] b = null;
            if (tempEajJz.getNR() == null) {
                b = null;
            } else {
                b = DecodeUtil.blobToBytes((BLOB) tempEajJz.getNR());
            }

            if (!org.springframework.util.StringUtils.isEmpty(tempEajJz.getYSBZ())
                    && ("2").equalsIgnoreCase(tempEajJz.getYSBZ()) && (BLOB) tempEajJz.getNR() != null) {
                byte[] before = DecodeUtil.blobToBytes((BLOB) tempEajJz.getNR());
                InputStream in = new ByteArrayInputStream(before);
                byte[] blobByte = DecodeUtil.deCompress(in);
                tempEajJz.setWJDX(Float.valueOf(blobByte.length));
                tempEajJz.setNR(blobByte);
            } else {
                tempEajJz.setNR(b);
            }
        }
        return tempEajJz;
    }

}
