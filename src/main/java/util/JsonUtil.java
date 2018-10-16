package util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json转化-工具类
 * Map<String, Object> toMap(String args)
 * json转对象 T fromString(String jsonString, Class<T> c)
 * JSON转LIST List<T> toList(String jsonString, Class<T> c)
 * JSON转LIST List<T> toMapList(String jsonString, Class<T> c)
 * <p>
 * ****************************************************
 * JSON转MAP Map<String, Object> toMapFromJsonStr(String args)
 * XML转JSON String toJsonFromXml(String xml)
 * JSON转XML String toXmlFromJson(String json)
 * JSON转STR String toJsonString(Object obj)
 * JSON转STR String toJsonFromObject(Object obj)
 * JSON转对象 T toBeanFromStr(String jsonString, Class<T> c)
 * JSON转LIST List<T> toListFromJsonStr(String args)
 */
public class JsonUtil {

    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static void main(String[] args) {
        JSON.toJSONString("");
    }

    /**
     * JSON转Map方法 (com.alibaba.fastjson)
     *
     * @param jsonStr json字符串
     * @return map
     */
    public static Map<String, Object> toMapFromJsonStr(String jsonStr) {
        if (StringUtil.isEmpty(jsonStr)) {
            return new HashMap<>();
        }
        Map<String, Object> argsMap;
        try {
            argsMap = JSONObject.parseObject(jsonStr, Map.class);
        } catch (Exception ex) {
            if (jsonStr.contains("\\")) {
                try {
                    jsonStr = jsonStr.replace("\\", "\\\\");
                    argsMap = JSONObject.parseObject(jsonStr, Map.class);
                } catch (Exception e) {
                    logger.error("JSON转Map出错，jsonStr=" + jsonStr, e);
                    argsMap = new HashMap<>();
                }
            } else {
                logger.error("JSON转Map出错，jsonStr=" + jsonStr, ex);
                argsMap = new HashMap<>();
            }
        }
        return argsMap;
    }

    /**
     * 转json字符串 (com.alibaba.fastjson)
     * jsonIgnore 无效
     * QuoteFieldNames———-输出key时是否使用双引号,
     * WriteMapNullValue——–是否输出值为null的字段,默认为false
     * WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
     * WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
     * WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
     * WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
     *
     * @param obj 传入参（map、json等）
     * @return json字符串
     */
    public static String toJsonFromObject(Object obj) {
        if (obj == null || "".equals(obj)) {
            return "";
        }
        try {
            return JSON.toJSONString(obj,SerializerFeature.WriteNullStringAsEmpty);
        } catch (Exception e) {
            logger.error("Json转对象出错，obj=" + obj,e);
            return null;
        }
    }

    /**
     * json 转对象
     *
     * @param jsonStr 字符串
     * @param c          class
     * @param <T>        转换对象
     * @return 对象
     */
    public static <T> T toBeanFromStr(String jsonStr, Class<T> c) {
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }
        try {
            return JSONObject.parseObject(jsonStr, c);
        } catch (Exception ex) {
            if (jsonStr.contains("\\")) {
                try {
                    jsonStr = jsonStr.replace("\\", "\\\\");
                    return JSONObject.parseObject(jsonStr, c);
                } catch (Exception e) {
                    logger.error("JSON转对象出错，jsonStr=" + jsonStr, e);
                }
            } else {
                logger.error("JSON转对象出错，jsonStr=" + jsonStr, ex);
            }
            return null;
        }
    }

    /**
     * json字符串转List(com.alibaba.fastjson)
     *
     * @param jsonStr json字符串
     * @return List
     */
    public static <T> List<T> toListFromJsonStr(String jsonStr,Class<T> clzz) {
        if (StringUtil.isEmpty(jsonStr)) {
            return new ArrayList();
        }
        List<T> argsMap;
        try {
            argsMap = JSONObject.parseObject(jsonStr, List.class);
        } catch (Exception ex) {
            if (jsonStr.contains("\\")) {
                try {
                    jsonStr = jsonStr.replace("\\", "\\\\");
                    return JSONObject.parseArray(jsonStr, clzz);
                } catch (Exception e) {
                    logger.error("JSON转List出错，jsonStr=" + jsonStr, e);
                }
            } else {
                logger.error("JSON转List出错，jsonStr=" + jsonStr, ex);
            }
            return new ArrayList();
        }
        return argsMap;
    }
}
