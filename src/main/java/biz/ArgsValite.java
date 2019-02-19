package biz;

import util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YScredit on 2019/1/2.
 */
public class ArgsValite {

    private static final String[] ARGS_KEYS = new String[]{"pageSize","pageNum","page","start","size","offset","startRow","rowNum"};

    public static void main(String[] args) {
        System.out.println("有数".length());
        System.out.println("12".length());
        Map<String,Object> itemMap = new HashMap();
        itemMap.put("entInfo","有数");
        System.out.println(valiteArgs(itemMap));
    }

    private static boolean valiteArgs(Map<String, Object> args) {

        String entInfo = "";

        if (args.containsKey("entInfo")) {
            entInfo = StringUtil.toString(args.get("entInfo"));
        } else {
            if (args.containsKey("entName")) {
                entInfo = StringUtil.toString(args.get("entName"));
            }
            if (args.containsKey("eid")) {
                entInfo = StringUtil.toString(args.get("eid"));
            }
            if (args.containsKey("key")) {
                entInfo = StringUtil.toString(args.get("key"));
            }
        }

        if(args.containsKey("pageNum")){
            Integer pageNum = StringUtil.toIntegerFromObj(args.get("pageNum"));
            if(pageNum > 1000){
                return false;
            }
        }

        for (String key : ARGS_KEYS){
            if(args.containsKey(key)){
                Integer pageNum = StringUtil.toIntegerFromObj(args.get(key));
                if(pageNum > 1000){
                    return false;
                }
            }
        }

        if (StringUtil.isNotEmpty(entInfo)) {
            if (entInfo.length() == 1) {
                return false;
            }
            if (entInfo.matches("[*|＊]+")) {
                return false;
            }
            if (entInfo.matches("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+")) {
                return false;
            }
//            if (ArgsHmd.contains(entInfo)) {
//                return false;
//            }
        }

        return true;
    }
}
