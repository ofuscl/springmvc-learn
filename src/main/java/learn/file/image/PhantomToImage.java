package learn.file.image;

import learn.file.pdf.HtmlToPdfInter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by yunfan on 2018/5/19.
 */
public class PhantomToImage {
    private static final Logger LOG = LoggerFactory.getLogger(HtmltoImg.class);

    private static final String TO_IMAGE_TOOL = "E:/wkhtmltopdf/bin/wkhtmltoimage";

    /**
     * html转pdf
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath) {

        File file = new File(destPath);
        File parent = file.getParentFile();
        // 如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }

        // 网页截屏
        StringBuilder cmd = new StringBuilder();
        cmd.append("E:\\phantomjs_ccb\\phantomjs");
        cmd.append(" ");
        cmd.append("E:\\phantomjs_ccb\\ccb_screenshot_test.js");
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInter error = new HtmlToPdfInter(
                    proc.getErrorStream());
            HtmlToPdfInter output = new HtmlToPdfInter(
                    proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
            LOG.info("HTML2PDF成功，参数---html路径：{},pdf保存路径 ：{}", new Object[] {srcPath, destPath });
        } catch (Exception e) {
            LOG.error("HTML2PDF失败，srcPath地址：{},错误信息：{}", new Object[]{srcPath, e.getMessage()});
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
//        String url = "file:///E:\\ccb-cloud\\craw_screenshot\\html\\q3ae3348d78be4f528786f036c3c80222\\20180615141302\\bzxr\\q3ae3348d78be4f528786f036c3c80222_bzxr_0.html";
        String url = "file:///F://temp//xyzj.html";
//        String url = "http://www.zjcredit.gov.cn/";
        String path = "F://temp//1.png";

        convert(url,path);
    }
}
