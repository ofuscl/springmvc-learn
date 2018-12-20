package learn;

import org.apache.commons.lang.StringUtils;

/**
 * Created by YScredit on 2018/12/13.
 */
public class StringUtilsTest {

    public static void main(String[] args) {

        System.out.println("大小写函数-首字母大写："+StringUtils.capitalize("abc"));
        System.out.println("大小写函数-首字母小写："+StringUtils.uncapitalize("ABc"));
        System.out.println("大小写函数-全部大写："+StringUtils.upperCase("aBc"));
        System.out.println("大小写函数-全部小写："+StringUtils.lowerCase("ABc"));
        System.out.println("大小写函数-大小写互转："+StringUtils.swapCase("ABc"));
        System.out.println("大小写函数-是否全部大写："+StringUtils.isAllUpperCase("ABc"));
        System.out.println("大小写函数-是否全部小写："+StringUtils.isAllLowerCase("ab  c"));

        System.out.println("删除函数-删除指定字符："+StringUtils.remove("queued", 'u'));
        System.out.println("删除函数-删除指定字符串："+StringUtils.remove("queued", "ue"));
        System.out.println("删除函数-删除结尾匹配的字符串,找都不到返回原字符串："+StringUtils.removeEnd("www.domain.com", ".com"));
        System.out.println("删除函数-忽略大小写的："+StringUtils.removeEndIgnoreCase("www.domain.com", ".COM"));
        System.out.println("删除函数-删除所有空白（好用）："+StringUtils.deleteWhitespace("   ab  c  "));

        System.out.println("缩短省略函数-最大长度为6："+StringUtils.abbreviate("abcdefg", 6));
        System.out.println("匹配计数函数-包含字符个数："+StringUtils.countMatches("abba", "b"));
    }
}
