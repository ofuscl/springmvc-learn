package demo.util.comm;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 目录：
 * 1、isEmpty(str);  字符串是否为空（含：null、""、"    "、"\t"、"\n"、"null"）
 * 2、isNotEmpty(str);   字符串不为空?（含：null、""、"    "、"\t"、"\n"、"null"）
 * 3、toString(Object);  Object -> String （null 返回 null，结果trim()）
 * 4、toStringNotNull(Object);   Object -> String （若isEmpty则返回 ""，结果未trim()）
 * 6、toIntegerFromObj(Object);   Object -> Integer （若isEmpty则返回 0，否则Integer.parseInt()）
 * 7、toDoubleFromObj(Object);   Object -> Double （若isEmpty则返回 0，否则Integer.parseInt()）
 * 8、toDoubleStrFromObj(Object);   Object -> String （toStrFromDouble(toDoubleFromObj())，Object转换为Double类型字符串）
 * 9、toStrFromDouble(Double);   Double -> String （null、0 返回 ""，否则String.format()）
 * 10、isNumeric(String);   判断字符串是否为纯数字(若isEmpty、有小数点、以正负号开头 false，否则true)
 * 11、removeBlank(String);   去掉字符串中的所有空格（\\s*|\t|\r|\n）
 * 12、removeUndefined(String);   去掉字符串中的undefined和undefined|
 * 13、convertToChineseKuohao(String);   转换字符串中的括号为中文括号
 * 15、replaceSpeSymbol(String);   特殊字符替换
 * 16、isChinese(String);   判断是否为中文字符串
 * 17、isChinese(char);   判断是否为中文字符
 * 18、isConSpeCharacters(String);   判断是否包含特殊字符，如：“”￥！？等
 * 19、convert(String);   特殊字符转换：ö->&ouml;、ü->&uuml;
 * 20、convertUnicode(String);   特殊字符转换：&ouml;->ö、&uuml->ü;
 * 21、replaceSpots(String);   将各种类型的圆点替换成中文圆点
 * 21、getNames(String);   将包含括号的返回既有中文括号，也有英文括号的SQL条件字符串：A(a)B——>'A(a)B,A（a）B'
 * 22、toSqlStrFromList(String);   将字符串集合转换成SQL IN 中字符串，结果：'a','b','c'
 *
 * Deprecated：
 * 5、toInteger(Object);   Object >> Integer （null和""返回0，Integer.parseInt()）
 * 14、replace(String);   特殊字符替换
 */
public class StringUtil extends StringUtils {


    public static final String EMPTY = "";

    /**
     * 判断-字符串为空（null、""、"    "、"\t"、"\n"、"null"）
     *
     * @param s 需要判断的字符串
     * @return boolean  true：是，false：不是
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty() || s.equalsIgnoreCase("null");
    }

    /**
     * 判断-字符串不为空（含：null、""、"    "、"\t"、"\n"、"null"）
     *
     * @param s 需要判断的字符串
     * @return boolean  true：不是，false：是
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * 转化-Object转换为String（null仍返回null，结果trim()）
     *
     * @param obj 待转换对象
     * @return String
     */
    public static String toString(Object obj) {
        return obj == null ? null : obj.toString().trim();
    }

    /**
     * 转化-字符串为String（null、空字符串、"null" 均返回""，结果未trim()）
     *
     * @param obj 待转化对象
     * @return String
     */
    public static String toStringNotNull(Object obj) {
        if (obj == null) {
            return EMPTY;
        }
        String s = obj.toString();
        return isEmpty(s) ? EMPTY : s;
    }

    /**
     * 转化-字符串为Integer。（不含null）
     * <br>
     * 建议使用toIntegerFromObj(obj)
     *
     */
    @Deprecated
    public static Integer toInteger(Object obj) {
        return obj == null || obj == EMPTY ? 0 : Integer.parseInt(obj.toString());
    }

    /**
     * 转化-任意对象为Integer，不符合格式均返回0
     * <br>
     * null、空字符串均返回 0
     *
     * @param obj 待转化对象
     * @return Integer
     */
    public static Integer toIntegerFromObj(Object obj) {
        String str = toStringNotNull(obj);
        return RegularUtil.validate(str, RegularUtil.IS_INTEGER) ? Integer.parseInt(str) : 0;
    }

    /**
     * 转化-任意对象为Double，不符合格式均返回0.0
     * <br>
     * null、空字符串均返回 0.0
     *
     * @param obj 待转化对象
     * @return Double
     */
    public static Double toDoubleFromObj(Object obj) {
        String str = toStringNotNull(obj);
        return RegularUtil.validate(str, RegularUtil.IS_DOUBLE) ? Double.parseDouble(str) : 0.0;
    }

    /**
     * 转化-Double类型数据转换为字符串。（null、空字符串、0.0 返回 ""）
     *
     * @param obj 需要转化的对象
     * @return String
     */
    public static String toDoubleStrFromObj(Object obj) {
        return toStrFromDouble(toDoubleFromObj(obj));
    }

    /**
     * 转化-Double转换为字符串（null、0.0 返回 ""）
     *
     * @param d 待转换double数据
     * @return String
     */
    public static String toStrFromDouble(Double d) {
        return d == null || d == 0.00 ? EMPTY : String.format("%.2f", d);
    }


    /**
     * 判断-字符串是否为数字
     * <p>false：null、""、"    "、"\t"、"\n"、"null"、带小数点、以=/-(正/负号)开头也不行</p>
     * <p>true：1、123</p>
     *
     * @param str   待判断字符串
     * @return boolean  true-是；false-否
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 替换-字符串-去除全半角空格（null、空字符串、"null" 返回""）
     *
     * @param str 待转化字符串
     * @return String
     */
    public static String removeBlank(String str) {
        if (isEmpty(str)) {
            return EMPTY;
        }
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        return m.replaceAll(EMPTY);
    }


    // ======================转换============================================
    /**
     * 替换-字符串-去除Undefined（null、空字符串、"null" 返回""）
     *
     * @param str 待转化字符串
     * @return String
     */
    public static String removeUndefined(String str) {
        if (isEmpty(str)) {
            return "";
        }
        str = StringUtils.replace(str, "undefined|", EMPTY);
        str = StringUtils.replace(str, "undefined", EMPTY);
        return str;
    }


    /**
     * 转换-字符串为中文括号。
     */
    public static String convertToChineseKuohao(String str) {
        if (isEmpty(str)) {
            return "";
        }
        if (str.contains("(")) {
            str = StringUtils.replace(str, "(", "（");
        }
        if (str.contains(")")) {
            str = StringUtils.replace(str, ")", "）");
        }

        // 全角英字转半角
        return fullToHalf(str);
    }

    /**
     * 转化-字符串-去除特殊符号。
     */
    public static String replaceSpeSymbol(String str) {
        if (isEmpty(str)) {
            return "";
        }

        str = StringUtils.replace(str, "\r\n", " ");
        str = StringUtils.replace(str, "\u0006", EMPTY);
        str = StringUtils.replace(str, "\u0000", EMPTY);
        str = StringUtils.replace(str, "\u001C", EMPTY);
        str = StringUtils.replace(str, "\u001c", EMPTY);
        str = StringUtils.replace(str, "\\u0006", EMPTY);
        str = StringUtils.replace(str, "\\u0000", EMPTY);
        str = StringUtils.replace(str, "\\u001C", EMPTY);
        str = StringUtils.replace(str, "\\u001c", EMPTY);
        //将姓名中的点 "&#8226;"替换成中文格式"·"
        str = StringUtils.replace(str, "&#8226;", SPOTS);
        str = StringUtils.replace(str, "\\\\u2022", SPOTS);
        str = StringUtils.replace(str, "\\u2022", SPOTS);
        str = StringUtils.replace(str, "\u2022", SPOTS);
        str = StringUtils.replace(str, "u2022", SPOTS);

        return str;
    }

    /**
     * 判断-是否为中文汉字（根据Unicode编码）
     */
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断-是否为中文汉字（根据Unicode编码）
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    /**
     * 判断-是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isConSpeCharacters(String str) {
        String regEx = "[`~!@#$%^&*+=|';'//[//].<>/?~！@#￥%……&*——+|【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

	/**
	 * 比对字符串处理。
	 * 去除特殊字符
	 *
	 */
	public static String getRegExStr(String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}
		// 去除特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?！￥……（）—\\-［］【】‘；：”“’。，、？　 \\\\～·＠％＾｛＋＼／｜＂＇＜＞－《》＃＿．＊_]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 特殊字符转换器。
	 */
	public static String convert(String str){

        if (isEmpty(str)) {
            return str;
        }

        if (str.contains("ö")) {
            str = str.replace("ö", "&ouml;");
        }
        if (str.contains("ü")) {
            str = str.replace("ü", "&uuml;");
        }

        return str;
    }

    /**
     * 特殊字符转换器。
     */
    public static String convertUnicode(String str) {

        if (isEmpty(str)) {
            return str;
        }

        if (str.contains("&ouml;")) {
            str = str.replace("&ouml;", "ö");
        }
        if (str.contains("&uuml;")) {
            str = str.replace("&uuml;", "ü");
        }

        return str;
    }

    /** 中文圆点 */
    private static final String SPOTS = "·";

    /**
     * add 2016.11.17：替换姓名中的圆点
     *
     * @param name
     * @return
     */
    public static String replaceSpots(String name) {
        if (name == null || EMPTY.equals(name)) {
            return EMPTY;
        }
        return name.trim().replaceAll(RegularUtil.SPOTS, SPOTS);
    }

    /**
     * 将名称中的括号转换，转换成既有中文又有英文的
     * <br>
     * A(a)B——>'A(a)B,A（a）B'
     * @param key   待转换名称
     * @return String   转换后名称
     */
    public static String getNames(String key) {
        if (isEmpty(key)) {
            return "''";
        }
        key = key.trim();
        StringBuffer sb = new StringBuffer();
        sb.append("'").append(key).append("'");
        if (key.contains("(")) {
            String newKey = StringUtils.replace(key, "(", "（");
            newKey = StringUtils.replace(newKey, ")", "）");
            sb.append(",'").append(newKey).append("'");
        } else if (key.contains("（")) {
            String newKey = StringUtils.replace(key, "（", "(");
            newKey = StringUtils.replace(newKey, "）", ")");
            sb.append(",'").append(newKey).append("'");
        }
        return sb.toString();
    }

    /**
     * 转换-将字符串集合转换为SQL中的字符串（即转换为：'a','b','c'；list为null或者空的时候返回""；null、空字符串、"null"跳过）
     *
     * @param list 待转换集合
     * @return String   'a','b','c'
     */
    public static String toSqlStrFromList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            if (isNotEmpty(s)) {
                sb.append("'");
                sb.append(s);
                sb.append("',");
            }
        }
        return sb.length() <= 0 ? "" : sb.substring(0, sb.length() - 1);
    }

    /**
     * 判断对象中的字段是否全部为空
     * @param obj   用于判断的对象
     * @return booleam  true：是，false：否
     */
    public static boolean checkObjFieldIsNull(Object obj) {

        boolean flag = false;
        try {
            for (Field f : obj.getClass().getDeclaredFields()) {
                if ("serialVersionUID".equals(f.getName())) {
                    continue;
                }
                f.setAccessible(true);
                Object x = f.get(obj);
                if (x != null && x instanceof String && !EMPTY.equals(x)) {
                    flag = true;
                    break;
                } else if (x != null && x instanceof List) {
                    if (!((List) x).isEmpty()) {
                        flag = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            flag = true;
        }

        return !flag;
    }

    /**
     *  全角转半角
     *
     */
    public static String fullToHalf(String string) {
        if (isEmpty(string)) {
            return string;
        }

        char[] charArray = string.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if(!RegularUtil.validate(String.valueOf(charArray[i]),RegularUtil.SBC_CHAR)){
                continue;
            }
            if (charArray[i] == 12288) {
                charArray[i] =' ';
            } else if (charArray[i] >= ' ' &&
                    charArray[i]  <= 65374) {
                charArray[i] = (char) (charArray[i] - 65248);
            } else {

            }
        }
        return new String(charArray);
    }

    /**
     *  半角转全角。
     *
     */
    public static String halfToFull(String value) {
        if (isEmpty(value)) {
            return "";
        }
        char[] cha = value.toCharArray();

        /**
         * full blank space is 12288, half blank space is 32
         * others :full is 65281-65374,and half is 33-126.
         */
        for (int i = 0; i < cha.length; i++) {
            if (cha[i] == 32) {
                cha[i] = (char) 12288;
            } else if (cha[i] < 127) {
                cha[i] = (char) (cha[i] + 65248);
            }
        }
        return new String(cha);
    }

    public static void main(String[] args) {
        List<String> xxList = new ArrayList<>();
        xxList.add("1");
        xxList.add(null);
        xxList.add("");
        System.out.print(fullToHalf("ＴＣＬ"));


    }

}
